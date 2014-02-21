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

import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;
import com.skelril.OSBL.instruction.Instruction;
import com.skelril.OSBL.instruction.InstructionProcessor;
import com.skelril.OSBL.interfaces.DamageInstructable;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalControllable extends LocalEntity implements DamageInstructable {

    /*
     * Passive Effect System Variables
    */
    public final List<Instruction> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<Instruction> damageInstructions = new ArrayList<>();
    public final List<Instruction> damagedInstructions = new ArrayList<>();

    public LocalControllable() {
    }

    /*
     * Targeting System
     */
    public abstract void setTarget(LocalEntity target);
    public abstract LocalEntity getTarget();

    /*
     * Passive Effect System
     */
    public void processEffects() {
        InstructionProcessor.process(passiveInstructions, this);
    }

    /*
     * Damage System Methods
     */
    @Override
    public void damage(LocalEntity toHit, AttackDamage damage) {
        InstructionProcessor.process(damageInstructions, this, toHit, damage);
    }
    @Override
    public void damaged(DamageSource damager, AttackDamage damage) {
        InstructionProcessor.process(damagedInstructions, this, damager, damage);
    }
}
