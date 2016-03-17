/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.skelril.openboss;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import java.util.Optional;

public class BossListener<T extends Living, K extends EntityDetail> {

    private final BossManager<T, K> manager;
    private final Class<T> clazz;

    public BossListener(BossManager<T, K> manager, Class<T> clazz) {
        this.manager = manager;
        this.clazz = clazz;
    }

    @Listener(order = Order.LAST)
    public void onEntityDamage(DamageEntityEvent event) {
        processDamage(event);
        processDamaged(event);
    }

    private void processDamaged(DamageEntityEvent event) {
        Optional<Boss<T, K>> boss = lookup(event.getTargetEntity());
        if (boss.isPresent()) {
            manager.callDamaged(boss.get(), event);
        }
    }

    private void processDamage(Entity entity, DamageEntityEvent event) {
        if (entity instanceof Living) {
            Optional<Boss<T, K>> boss = lookup(entity);
            if (boss.isPresent()) {
                manager.callDamage(boss.get(), event.getTargetEntity(), event);
            }
        }
    }

    private void processDamage(DamageEntityEvent event) {
        for (DamageSource src : event.getCause().allOf(DamageSource.class)) {
            if (src instanceof EntityDamageSource) {
                if (src instanceof IndirectEntityDamageSource) {
                    processDamage(((IndirectEntityDamageSource) src).getIndirectSource(), event);
                } else {
                    processDamage(((EntityDamageSource) src).getSource(), event);
                }
            }
        }
    }

    @Listener(order = Order.LAST)
    public void onEntityDeath(DestructEntityEvent.Death event) {
        Optional<Boss<T, K>> boss = lookup(event.getTargetEntity());
        if (boss.isPresent()) {
            manager.unbind(boss.get());
        }
    }


    public Optional<Boss<T, K>> lookup(Entity entity) {
        if (clazz.isInstance(entity)) {
            return manager.updateLookup(clazz.cast(entity));
        }
        return Optional.empty();
    }
}
