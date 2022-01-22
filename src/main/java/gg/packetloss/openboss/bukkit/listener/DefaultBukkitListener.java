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

package gg.packetloss.openboss.bukkit.listener;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import gg.packetloss.openboss.bukkit.BukkitBossDeclaration;
import gg.packetloss.openboss.bukkit.entity.BukkitEntity;
import gg.packetloss.openboss.bukkit.util.BukkitAttackDamage;
import gg.packetloss.openboss.bukkit.util.BukkitDamageSource;
import gg.packetloss.openboss.entity.EntityDetail;
import gg.packetloss.openboss.entity.LocalControllable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

public class DefaultBukkitListener<T extends EntityDetail> implements BukkitListener {

    private final Plugin declarer;
    private BukkitBossDeclaration<T> declaration;

    public DefaultBukkitListener(Plugin declarer, BukkitBossDeclaration<T> declaration) {
        this.declarer = declarer;
        this.declaration = declaration;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity hurt = event.getEntity();
        BukkitAttackDamage damage = new BukkitAttackDamage(event);

        LocalControllable<T> boss = getBoss(new BukkitEntity<>(hurt));

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
            boss = getBoss(new BukkitEntity<>(damager));

            // A boss of this type attacked
            if (boss != null && event.getDamage() > 0) {
                declaration.damage(boss, new BukkitEntity<>(hurt), damage);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEnityDeath(EntityDeathEvent event) {
        Entity dead = event.getEntity();

        LocalControllable<T> boss = declaration.getBound(new BukkitEntity<>(dead));
        if (boss != null) {
            declaration.unbind(boss);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityAdd(EntityAddToWorldEvent event) {
        Entity created = event.getEntity();

        BukkitEntity<?> boss = new BukkitEntity<>(created);
        // Rebind if this is an unbound controllable.
        if (isUnboundControllable(boss)) {
            // Delay to avoid possible concurrent modification during entity processing.
            declarer.getServer().getScheduler().runTask(declarer, () -> declaration.tryRebind(boss));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemove(EntityRemoveFromWorldEvent event) {
        Entity dead = event.getEntity();

        LocalControllable<T> boss = declaration.getBound(new BukkitEntity<>(dead));
        // Unbind if this is a bound controllable.
        if (boss != null) {
            // Delay to avoid possible concurrent modification during entity processing.
            declarer.getServer().getScheduler().runTask(declarer, () -> declaration.silentUnbind(boss));
        }
    }

    private boolean isUnboundControllable(BukkitEntity<?> entity, LocalControllable<T> controllable) {
        return controllable == null && declaration.matchesBind(entity);
    }

    private boolean isUnboundControllable(BukkitEntity<?> entity) {
        return isUnboundControllable(entity, declaration.getBound(entity));
    }

    private LocalControllable<T> getBoss(BukkitEntity<?> entity) {
        LocalControllable<T> controllable = declaration.getBound(entity);
        if (isUnboundControllable(entity, controllable)) {
            // Attempt to rebind/reload the entity.
            controllable = declaration.tryRebind(entity);

            // If we're still failing to find the entity remove it.
            if (controllable == null) {
                entity.getBukkitEntity().remove();
                return null;
            }
        }
        return controllable;
    }
}
