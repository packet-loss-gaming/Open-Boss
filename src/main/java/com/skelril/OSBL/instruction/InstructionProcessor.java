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
        for (Instruction instruction : instructions) {
            if (processChained(instruction, owner, relatedObjects)) return;
        }
    }

    private static boolean processParams(InputParameter parameter, boolean detailed) {
        boolean allowsDetail = parameter.allowsDetailed();
        boolean allowsUndetailed = parameter.allowsUndetailed();

        return (detailed && allowsDetail) || (!detailed && allowsUndetailed);
    }

    private static boolean processChained(Instruction instruction, LocalEntity owner, Object... relatedObjects) {
        final boolean detailed = owner != null;
        boolean terminal = false;
        Instruction next = instruction;
        while (next != null && processParams(next.params(), detailed)) {
            InstructionResult r = next.execute(owner, relatedObjects);
            if (r != null) {
                ResultType type = r.getResult();
                if (type.isTerminal()) terminal = true;
            }
            next = r == null ? null : r.next();
        }
        return terminal;
    }
}
