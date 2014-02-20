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

package com.skelril.OSBL;

import com.skelril.OSBL.entity.Boss;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.instruction.Instruction;
import com.skelril.OSBL.instruction.InstructionProcessor;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public abstract class BossDeclaration {

    /*
     * Damage System Variables
     */
    public List<Instruction> damageInstructions;
    public List<Instruction> damagedInstructions;

    public BossDeclaration() {
        this.damageInstructions = new ArrayList<>();
        this.damagedInstructions = new ArrayList<>();
    }

    /*
     * Binding System
     */
    public abstract void bind(Boss boss);
    public abstract void unbind(Boss boss);

    /*
     * Damage System Methods
     */
    public void damage(Boss attacker, LocalEntity toHit, AttackDamage damage) {
        InstructionProcessor.process(damageInstructions);
    }

    public void damaged(Boss defender, DamageSource damager, double damage) {
        InstructionProcessor.process(damagedInstructions);
    }
}
