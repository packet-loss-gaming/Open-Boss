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

package com.skelril.OSBL.bukkit.entity;

import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public class BukkitControllable<T extends EntityDetail, K extends Entity> extends LocalControllable<T, BukkitEntity, BukkitEntity<K>> {

    protected K controlled;

    public BukkitControllable(K controlled, T detail) {
        super(detail);
        this.controlled = controlled;
    }

    @Override
    public void setTarget(BukkitEntity target) {
        boolean isNull = target == null;
        Entity newTarget = isNull ? null : target.getBukkitEntity();
        if (controlled instanceof Monster && (isNull || newTarget instanceof LivingEntity)) {
            ((Monster) controlled).setTarget(isNull ? null : (LivingEntity) newTarget);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public BukkitEntity getTarget() {
        if (controlled instanceof Monster) {
            return new BukkitEntity<>(((Monster) controlled).getTarget());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public BukkitEntity<K> getLocalEntity() {
        return new BukkitEntity<>(controlled);
    }

    @Override
    public boolean isValid() {
        return controlled.isValid();
    }
}
