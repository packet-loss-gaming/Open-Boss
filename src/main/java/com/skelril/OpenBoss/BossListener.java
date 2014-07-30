/*
 * Copyright (c) 2014 Wyatt Childers.
 *
 * This file is part of Open Boss.
 *
 * Open Boss is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open Boss is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Open Boss.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skelril.OpenBoss;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BossListener implements Listener {

    private BossManager manager;

    public BossListener(BossManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Boss boss = lookup(event.getEntity());
        if (boss != null) {
            manager.callDamaged(boss, event);
            return;
        }

        if (event instanceof EntityDamageByEntityEvent) {
            handleEDBE((EntityDamageByEntityEvent) event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        Boss boss = lookup(event.getEntity());
        if (boss != null) {
            manager.unbind(boss);
        }
    }

    private void handleEDBE(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Projectile) {
            ProjectileSource source = ((Projectile) damager).getShooter();
            if (source instanceof LivingEntity) {
                damager = (Entity) source;
            }
        }

        Boss boss = lookup(damager);
        if (boss != null) {
            manager.callDamage(boss, event);
        }
    }


    public Boss lookup(Entity entity) {
        Boss boss = null;
        if (entity instanceof LivingEntity) {
            boss = manager.updateLookup((LivingEntity) entity);
        }
        return boss;
    }
}
