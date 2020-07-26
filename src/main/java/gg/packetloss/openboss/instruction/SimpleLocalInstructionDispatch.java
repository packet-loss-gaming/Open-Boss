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

package gg.packetloss.openboss.instruction;

import gg.packetloss.openboss.entity.EntityDetail;
import gg.packetloss.openboss.entity.LocalControllable;
import gg.packetloss.openboss.entity.LocalEntity;
import gg.packetloss.openboss.util.AttackDamage;
import gg.packetloss.openboss.util.DamageSource;

public class SimpleLocalInstructionDispatch<T extends EntityDetail> implements LocalInstructionDispatch<T> {
    @Override
    public InstructionProcessor<T, PassiveInstruction<T>> passive(LocalControllable<T> boss) {
        return new InstructionProcessor<T, PassiveInstruction<T>>() {
            @Override
            public InstructionResult<T, PassiveInstruction<T>> process(PassiveInstruction<T> instruction) {
                return instruction.process(boss);
            }
        };
    }

    @Override
    public InstructionProcessor<T, DamageInstruction<T>> damage(LocalControllable<T> boss, LocalEntity toHit, AttackDamage damage) {
        return new InstructionProcessor<T, DamageInstruction<T>>() {
            @Override
            public InstructionResult<T, DamageInstruction<T>> process(DamageInstruction<T> instruction) {
                return instruction.process(boss, toHit, damage);
            }
        };
    }

    @Override
    public InstructionProcessor<T, DamagedInstruction<T>> damaged(LocalControllable<T> boss, DamageSource hitBy, AttackDamage damage) {
        return new InstructionProcessor<T, DamagedInstruction<T>>() {
            @Override
            public InstructionResult<T, DamagedInstruction<T>> process(DamagedInstruction<T> instruction) {
                return instruction.process(boss, hitBy, damage);
            }
        };
    }
}
