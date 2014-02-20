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

public abstract class Boss implements DamageInstructable {

    /*
     * Damage System Variables
     */
    public List<Instruction> damageInstructions;
    public List<Instruction> damagedInstructions;

    public Boss() {
        this.damageInstructions = new ArrayList<>();
        this.damagedInstructions = new ArrayList<>();
    }

    /*
     * General Inquiry
     */
    public abstract LocalEntity getLocalEntity();
    public abstract boolean isValid();

    /*
     * Targeting System
     */
    public abstract void setTarget(LocalEntity target);
    public abstract LocalEntity getTarget();

    /*
     * Damage System Methods
     */
    @Override
    public void damage(LocalEntity toHit, AttackDamage damage) {
        InstructionProcessor.process(damageInstructions);
    }
    @Override
    public void damaged(DamageSource damager, double damage) {
        InstructionProcessor.process(damagedInstructions);
    }
}
