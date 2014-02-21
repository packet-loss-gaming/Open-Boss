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

import com.skelril.OSBL.bukkit.util.BukkitDamageSource;
import com.skelril.OSBL.bukkit.util.BukkitUtil;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;

public abstract class BukkitControllable extends LocalControllable {

    protected Damageable controlled;

    public BukkitControllable(Damageable controlled) {
        this.controlled = controlled;
    }

    @Override
    public void setTarget(LocalEntity target) {
        boolean isNull = target == null;
        assert isNull || target instanceof BukkitEntity;
        Entity newTarget = isNull ? null : ((BukkitEntity) target).getBukkitEntity();
        if (controlled instanceof Monster && (isNull || newTarget instanceof LivingEntity)) {
            ((Monster) controlled).setTarget(isNull ? null : (LivingEntity) newTarget);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public LocalEntity getTarget() {
        if (controlled instanceof Monster) {
            return new BukkitEntity<>(((Monster) controlled).getTarget());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void processEffects() {
        super.processEffects();
    }

    @Override
    public void damage(LocalEntity toHit, AttackDamage damage) {
        assert toHit instanceof BukkitEntity;
        super.damage(toHit, damage);
    }

    @Override
    public void damaged(DamageSource damager, double damage) {
        assert damager instanceof BukkitDamageSource;
        super.damaged(damager, damage);
    }

    @Override
    public LocalEntity getLocalEntity() {
        return new BukkitEntity<>(controlled);
    }

    @Override
    public boolean isValid() {
        return controlled.isValid();
    }

    @Override
    public boolean equals(Object entity) {
        return entity != null && controlled.equals(BukkitUtil.getBukkitEntity(entity));
    }
}
