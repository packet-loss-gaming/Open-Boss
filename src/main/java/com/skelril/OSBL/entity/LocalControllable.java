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

package com.skelril.OSBL.entity;

import com.skelril.OSBL.instruction.*;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;
import com.skelril.OSBL.entity.interfaces.DamageInstructable;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalControllable extends LocalEntity implements DamageInstructable {

    private final LocalInstructionDispatch dispatch;

    /*
     * Passive Effect System Variables
    */
    public final List<PassiveInstruction> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<DamageInstruction> damageInstructions = new ArrayList<>();
    public final List<DamagedInstruction> damagedInstructions = new ArrayList<>();

    public LocalControllable() {
        this(new SimpleLocalInstructionDispatch());
    }

    public LocalControllable(LocalInstructionDispatch dispatch) {
        this.dispatch = dispatch;
    }

    /*
     * Targeting System
     */
    public abstract void setTarget(LocalEntity target);
    public abstract LocalEntity getTarget();

    /*
     * Passive Effect System
     */
    public void process() {
        dispatch.passive(this).process(passiveInstructions);
    }

    /*
     * Damage System Methods
     */
    @Override
    public void damage(LocalEntity toHit, AttackDamage damage) {
        dispatch.damage(this, toHit, damage).process(damageInstructions);
    }
    @Override
    public void damaged(DamageSource damager, AttackDamage damage) {
        dispatch.damaged(this, damager, damage).process(damagedInstructions);
    }
}
