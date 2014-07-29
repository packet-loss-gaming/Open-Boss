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

package com.skelril.OSBL.bukkit.util;

import com.skelril.OSBL.block.LocalBlock;
import com.skelril.OSBL.bukkit.block.BukkitBlock;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.DamageSource;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class BukkitDamageSource extends DamageSource {

    private EntityDamageEvent.DamageCause cause;
    private Entity damagingEntity = null;
    private Block damagingBlock = null;

    public BukkitDamageSource(EntityDamageEvent.DamageCause cause) {
        super();
        this.cause = cause;
    }

    public BukkitDamageSource(Entity damagingEntity, EntityDamageEvent.DamageCause cause) {
        this(cause);
        this.damagingEntity = damagingEntity;
    }

    public BukkitDamageSource(Block damagingBlock, EntityDamageEvent.DamageCause cause) {
        this(cause);
        this.damagingBlock = damagingBlock;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    @Override
    public boolean involvesEntity() {
        return damagingEntity != null;
    }

    @Override
    public LocalEntity getDamagingEntity() {
        return new BukkitEntity<>(damagingEntity);
    }

    @Override
    public boolean involvesBlock() {
        return damagingBlock != null;
    }

    @Override
    public LocalBlock getDamagingBlock() {
        return new BukkitBlock(damagingBlock);
    }
}
