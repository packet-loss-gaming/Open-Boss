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

package com.skelril.openboss;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
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

    private void processDamage(DamageEntityEvent event) {
        for (Living living : event.getCause().allOf(Living.class)) {
            Optional<Boss<T, K>> boss = lookup(living);
            if (boss.isPresent()) {
                manager.callDamage(boss.get(), event.getTargetEntity(), event);
            }
        }
    }

    @Listener(order = Order.LAST)
    public void onEntityDeath(DestructEntityEvent.Death event) {
        Optional<Boss<T, K>> boss = lookup(event.getTargetEntity());
        if (boss != null) {
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
