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

import com.skelril.OSBL.entity.LocalEntity;

import java.util.Collection;

public class InstructionProcessor {

    public static void process(Collection<Instruction> instructions) {
        process(instructions, null);
    }

    public static void process(Collection<Instruction> instructions, LocalEntity owner, Object... relatedObjects) {
        final boolean hasBoundData = owner != null;
        for (Instruction instruction : instructions) {
            Instruction next = instruction;
            while (next != null) {
                if (next instanceof DeathInstruction) {
                    break;
                }
                if (hasBoundData && instruction instanceof BoundInstruction) {
                    next = ((BoundInstruction) next).execute(owner, relatedObjects);
                } else {
                    next = next.execute();
                }
            }
        }
    }
}
