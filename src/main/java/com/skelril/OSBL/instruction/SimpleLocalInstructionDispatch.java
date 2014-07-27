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

import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

public class SimpleLocalInstructionDispatch<T extends EntityDetail, A extends LocalEntity, K extends A, R extends LocalControllable<T, A, K>> implements LocalInstructionDispatch<T, A, K, R> {

    @Override
    public InstructionProcessor<PassiveInstruction<R>, InstructionResult<PassiveInstruction<R>>> passive(R boss) {
        return new InstructionProcessor<PassiveInstruction<R>, InstructionResult<PassiveInstruction<R>>>() {
            @Override
            public InstructionResult<PassiveInstruction<R>> process(PassiveInstruction<R> instruction) {
                return instruction.processFor(boss);
            }
        };
    }

    @Override
    public InstructionProcessor<DamageInstruction<R, A>, InstructionResult<DamageInstruction<R, A>>> damage(R boss, A toHit, AttackDamage damage) {
        return new InstructionProcessor<DamageInstruction<R, A>, InstructionResult<DamageInstruction<R, A>>>() {
            @Override
            public InstructionResult<DamageInstruction<R, A>> process(DamageInstruction<R, A> instruction) {
                return instruction.supply(toHit, damage).processFor(boss);
            }
        };
    }

    @Override
    public InstructionProcessor<DamagedInstruction<R>, InstructionResult<DamagedInstruction<R>>> damaged(R boss, DamageSource hitBy, AttackDamage damage) {
        return new InstructionProcessor<DamagedInstruction<R>, InstructionResult<DamagedInstruction<R>>>() {
            @Override
            public InstructionResult<DamagedInstruction<R>> process(DamagedInstruction<R> instruction) {
                return instruction.supply(hitBy, damage).processFor(boss);
            }
        };
    }
}
