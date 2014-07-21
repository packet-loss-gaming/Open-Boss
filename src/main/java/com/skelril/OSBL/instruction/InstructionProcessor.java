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

import java.util.Collection;

public abstract class InstructionProcessor<T extends EntityDetail, I extends Instruction<T>> {

    public void process(Collection<I> instructions) {
        for (I instruction : instructions) {
            if (processChained(instruction)) return;
        }
    }

    public abstract InstructionResult<T, I> process(I instruction);

    private  boolean processChained(I instruction) {
        boolean terminal = false;
        I next = instruction;
        while (next != null) {
            InstructionResult<T, I> r = process(instruction);
            if (r != null) {
                ResultState type = r.getResult();
                if (type.isTerminal()) terminal = true;
            }
            next = r == null ? null : r.next();
        }
        return terminal;
    }
}
