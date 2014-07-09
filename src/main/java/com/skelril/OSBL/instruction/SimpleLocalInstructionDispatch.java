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

package com.skelril.OSBL.instruction;

import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

public class SimpleLocalInstructionDispatch implements LocalInstructionDispatch {
    @Override
    public InstructionProcessor<PassiveInstruction> passive(LocalControllable boss) {
        return new InstructionProcessor<PassiveInstruction>() {
            @Override
            public InstructionResult<PassiveInstruction> process(PassiveInstruction instruction) {
                return instruction.process(boss);
            }
        };
    }

    @Override
    public InstructionProcessor<DamageInstruction> damage(LocalControllable boss, LocalEntity toHit, AttackDamage damage) {
        return new InstructionProcessor<DamageInstruction>() {
            @Override
            public InstructionResult<DamageInstruction> process(DamageInstruction instruction) {
                return instruction.process(boss, toHit, damage);
            }
        };
    }

    @Override
    public InstructionProcessor<DamagedInstruction> damaged(LocalControllable boss, DamageSource hitBy, AttackDamage damage) {
        return new InstructionProcessor<DamagedInstruction>() {
            @Override
            public InstructionResult<DamagedInstruction> process(DamagedInstruction instruction) {
                return instruction.process(boss, hitBy, damage);
            }
        };
    }
}
