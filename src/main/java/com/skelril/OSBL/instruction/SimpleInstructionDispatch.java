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

public class SimpleInstructionDispatch<T extends EntityDetail> extends SimpleLocalInstructionDispatch<T> implements InstructionDispatch<T> {
    @Override
    public InstructionProcessor<BindInstruction<T>> bind(LocalControllable<T> controllable) {
        return new InstructionProcessor<BindInstruction<T>>() {
            @Override
            public InstructionResult<BindInstruction<T>> process(BindInstruction<T> instruction) {
                return instruction.process(controllable);
            }
        };
    }

    @Override
    public InstructionProcessor<UnbindInstruction<T>> unbind(LocalControllable<T> controllable) {
        return new InstructionProcessor<UnbindInstruction<T>>() {
            @Override
            public InstructionResult<UnbindInstruction<T>> process(UnbindInstruction<T> instruction) {
                return instruction.process(controllable);
            }
        };
    }
}
