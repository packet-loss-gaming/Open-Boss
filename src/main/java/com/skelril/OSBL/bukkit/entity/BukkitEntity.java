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

import com.skelril.OSBL.entity.LocalEntity;
import org.bukkit.entity.Entity;

public class BukkitEntity<T extends Entity> implements LocalEntity<BukkitEntity<T>> {

    private T entity;

    public BukkitEntity(T entity) {
        this.entity = entity;
    }

    public T getBukkitEntity() {
        return entity;
    }

    @Override
    public BukkitEntity<T> getLocalEntity() {
        return new BukkitEntity<>(entity);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
