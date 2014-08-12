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

import org.apache.commons.lang.Validate;
import org.bukkit.entity.LivingEntity;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class Boss extends ProcessedComponent {

    private WeakReference<LivingEntity> entity;
    private final UUID entityID;
    private final EntityDetail detail;

    public Boss(LivingEntity entity, EntityDetail detail) {
        this.entity = new WeakReference<>(entity);
        this.entityID = entity.getUniqueId();
        this.detail = detail;
    }

    public LivingEntity getEntity() {
        return entity.get();
    }

    protected void setEntity(LivingEntity entity) {
        Validate.isTrue(entity.getUniqueId().equals(entityID));
        this.entity = new WeakReference<>(entity);
    }

    public UUID getEntityID() {
        return entityID;
    }

    public EntityDetail getDetail() {
        return detail;
    }
}
