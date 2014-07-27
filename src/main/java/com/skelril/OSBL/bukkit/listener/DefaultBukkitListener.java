/*
 * Copyright (c) 2014 Wyatt Childers.
 *
 * This file is part of OSBL.
 *
 * OSBL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OSBL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with OSBL.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skelril.OSBL.bukkit.listener;

import com.skelril.OSBL.bukkit.BukkitBossDeclaration;
import com.skelril.OSBL.bukkit.entity.BukkitControllable;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.bukkit.util.BukkitAttackDamage;
import com.skelril.OSBL.bukkit.util.BukkitDamageSource;
import com.skelril.OSBL.entity.EntityDetail;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DefaultBukkitListener<T extends EntityDetail, K extends Entity> implements BukkitListener {

    private BukkitBossDeclaration<T, K> declaration;
    private Class<K> type;

    public DefaultBukkitListener(BukkitBossDeclaration<T, K> declaration, Class<K> type) {
        assert declaration != null;
        this.declaration = declaration;
        this.type = type;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity hurt = event.getEntity();
        BukkitAttackDamage damage = new BukkitAttackDamage(event);

        BukkitControllable<T, K> boss = getBoss(hurt);

        // A boss of this type was harmed
        if (boss != null) {
            BukkitDamageSource attacker;
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (event instanceof EntityDamageByBlockEvent) {
                Block damager = ((EntityDamageByBlockEvent) event).getDamager();
                attacker = new BukkitDamageSource(damager, cause);
            } else if (event instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                if (damager instanceof Projectile) {
                    ProjectileSource source = ((Projectile) damager).getShooter();
                    if (source instanceof Entity) {
                        damager = (Entity) source;
                    }
                }
                attacker = new BukkitDamageSource(damager, cause);
            } else {
                attacker = new BukkitDamageSource(cause);
            }

            declaration.damaged(boss, attacker, damage);
        } else if (event instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if (damager instanceof Projectile) {
                ProjectileSource source = ((Projectile) damager).getShooter();
                if (source instanceof Entity) {
                    damager = (Entity) source;
                }
            }
            boss = getBoss(damager);

            // A boss of this type attacked
            if (boss != null && event.getDamage() > 0) {
                declaration.damage(boss, new BukkitEntity<>(hurt), damage);
            }
        }
    }

    private BukkitControllable<T, K> getBoss(Entity entity) {
        if (type.isInstance(entity)) {
            @SuppressWarnings("unchecked")
            BukkitEntity<K> boss = new BukkitEntity<>((K) entity);
            BukkitControllable<T, K> controllable = declaration.getBound(boss);
            if (controllable == null && declaration.matchesBind(boss)) {
                entity.remove();
                declaration.cleanup();
                return null;
            }
            return controllable;
        }
        return null;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEnityDeath(EntityDeathEvent event) {
        Entity dead = event.getEntity();

        BukkitControllable<T, K> boss = declaration.getBound(new BukkitEntity<>(dead));
        if (boss != null) {
            declaration.unbind(boss);
        }
    }
}
