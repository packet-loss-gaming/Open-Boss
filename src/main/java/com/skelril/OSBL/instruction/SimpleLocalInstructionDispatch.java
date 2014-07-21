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

public class SimpleLocalInstructionDispatch<T extends EntityDetail> implements LocalInstructionDispatch<T> {
    @Override
    public InstructionProcessor<PassiveInstruction<T>> passive(LocalControllable<T> boss) {
        return new InstructionProcessor<PassiveInstruction<T>>() {
            @Override
            public InstructionResult<PassiveInstruction<T>> process(PassiveInstruction<T> instruction) {
                return instruction.process(boss);
            }
        };
    }

    @Override
    public InstructionProcessor<DamageInstruction<T>> damage(LocalControllable<T> boss, LocalEntity toHit, AttackDamage damage) {
        return new InstructionProcessor<DamageInstruction<T>>() {
            @Override
            public InstructionResult<DamageInstruction<T>> process(DamageInstruction<T> instruction) {
                return instruction.process(boss, toHit, damage);
            }
        };
    }

    @Override
    public InstructionProcessor<DamagedInstruction<T>> damaged(LocalControllable<T> boss, DamageSource hitBy, AttackDamage damage) {
        return new InstructionProcessor<DamagedInstruction<T>>() {
            @Override
            public InstructionResult<DamagedInstruction<T>> process(DamagedInstruction<T> instruction) {
                return instruction.process(boss, hitBy, damage);
            }
        };
    }
}
