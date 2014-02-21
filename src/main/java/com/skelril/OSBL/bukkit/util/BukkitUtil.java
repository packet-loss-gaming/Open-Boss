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

import com.skelril.OSBL.bukkit.entity.BukkitControllable;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class BukkitUtil {

    public static Entity getBukkitEntity(Object entity) {
        Entity bukkitEntity = null;
        if (entity instanceof BukkitEntity) {
            bukkitEntity = ((BukkitEntity) entity).getBukkitEntity();
        } else if (entity instanceof BukkitControllable) {
            bukkitEntity = ((BukkitEntity) ((BukkitControllable) entity).getLocalEntity()).getBukkitEntity();
        } else if (entity instanceof Entity) {
            bukkitEntity = (Entity) entity;
        }
        return bukkitEntity;
    }

    public static UUID grabUUID(LocalEntity entity) {
        assert entity != null && entity instanceof BukkitEntity;
        return ((BukkitEntity) entity).getBukkitEntity().getUniqueId();
    }

    public static UUID grabUUID(LocalControllable entity) {
        assert entity != null && entity instanceof BukkitControllable;
        return grabUUID(entity.getLocalEntity());
    }
}
