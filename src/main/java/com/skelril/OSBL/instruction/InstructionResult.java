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

public class InstructionResult {

    private Instruction next;
    private ResultType resultType;

    public InstructionResult() {
        this.next = null;
        this.resultType = ResultType.END;
    }

    public InstructionResult(Instruction next) {
        this.next = next;
        this.resultType = ResultType.STANDARD;
    }

    public InstructionResult(Instruction next, ResultType resultType) {
        if (!resultType.executes()) {
            assert next == null;
        }
        this.next = next;
        this.resultType = resultType;
    }

    public Instruction next() {
        return next;
    }

    public ResultType getResult() {
        return resultType;
    }
}
