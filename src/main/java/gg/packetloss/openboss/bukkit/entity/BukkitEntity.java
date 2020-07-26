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

package gg.packetloss.openboss.bukkit.entity;

import gg.packetloss.openboss.bukkit.util.BukkitUtil;
import gg.packetloss.openboss.entity.LocalEntity;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class BukkitEntity<T extends Entity> extends LocalEntity {

    private T entity;

    public BukkitEntity(T entity) {
        this.entity = entity;
    }

    public T getBukkitEntity() {
        return entity;
    }

    @Override
    public UUID getUUID() {
        return entity.getUniqueId();
    }

    @Override
    public LocalEntity getLocalEntity() {
        return new BukkitEntity<>(entity);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public boolean equals(Object entity) {
        if (entity == null) {
            return this.entity == null;
        }
        return this.entity.equals(BukkitUtil.getBukkitEntity(entity));
    }
}
