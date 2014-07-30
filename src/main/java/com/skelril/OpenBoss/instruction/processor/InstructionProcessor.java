/*
 * Copyright (c) 2014 Wyatt Childers.
 *
 * This file is part of Open Boss.
 *
 * Open Boss is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open Boss is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Open Boss.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skelril.OpenBoss.instruction.processor;

import com.skelril.OpenBoss.InstructionResult;
import com.skelril.OpenBoss.condition.Condition;
import com.skelril.OpenBoss.instruction.Instruction;

import java.util.HashSet;
import java.util.Set;

public class InstructionProcessor<I extends Instruction<I, C>, C extends Condition> {

    protected Set<I> instructions = new HashSet<>();

    public void addInstruction(I instruction) {
        instructions.add(instruction);
    }

    public void remIntruction(I instruction) {
        instructions.remove(instruction);
    }

    public void processInstructions(C condition) {
        for (I instruction : instructions) {
            InstructionResult<?> next;
            do {
                next = instruction.process(condition);
            } while (next != null && next.getNext() != null);

            // If there is an instruction that reaches this point
            // the next instruction is null. This type of result
            // is considered a terminal result, and will exit
            // processing immediately.
            if (next != null) {
                return;
            }
        }
    }
}
