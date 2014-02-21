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

import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.instruction.Instruction;
import com.skelril.OSBL.instruction.InstructionProcessor;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public abstract class BossDeclaration {

    /*
     * Binding System Variables
     */
    public final List<Instruction> bindInstructions = new ArrayList<>();
    public final List<Instruction> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
     */
    public final List<Instruction> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<Instruction> damageInstructions = new ArrayList<>();
    public final List<Instruction> damagedInstructions = new ArrayList<>();

    /*
     * Binding System
     */
    public void bind(LocalControllable controllable) {
        InstructionProcessor.process(bindInstructions, controllable);
    }
    public abstract LocalControllable getBound(LocalEntity entity);
    public void unbind(LocalControllable controllable) {
        InstructionProcessor.process(unbindInstructions, controllable);
    }

    /*
     * Passive Effect System
     */
    public void processGeneralEffects() {
        InstructionProcessor.process(passiveInstructions);
    }
    public void processGeneralAndPersonalEffects(LocalControllable controllable) {
        processGeneralEffects();
        controllable.processEffects();
    }
    public void processGeneralAndAllPersonalEffects() {
        processGeneralEffects();
        processAllPersonalEffects();
    }
    public abstract void processAllPersonalEffects();

    /*
     * Damage System Methods
     */
    public void damage(LocalControllable attacker, LocalEntity toHit, AttackDamage damage) {
        InstructionProcessor.process(damageInstructions, attacker, toHit, damage);
        attacker.damage(toHit, damage);
    }

    public void damaged(LocalControllable defender, DamageSource damager, double damage) {
        InstructionProcessor.process(damagedInstructions, defender, damager, damage);
        defender.damaged(damager, damage);
    }
}
