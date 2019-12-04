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

package com.skelril.OSBL.bukkit.util;

import com.skelril.OSBL.util.AttackDamage;
import org.bukkit.event.entity.EntityDamageEvent;

public class BukkitAttackDamage extends AttackDamage {

    private EntityDamageEvent event;

    public BukkitAttackDamage(EntityDamageEvent event) {
        super();
        this.event = event;
    }

    public EntityDamageEvent getBukkitEvent() {
        return event;
    }

    @Override
    public double getDamage() {
        return event.getDamage();
    }

    @Override
    public boolean setDamage(double damage) {
        event.setDamage(damage);
        return !event.isCancelled();
    }

    @Override
    public boolean isCancelled() {
        return event.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        event.setCancelled(cancelled);
    }
}
