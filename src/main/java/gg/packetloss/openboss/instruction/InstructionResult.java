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

public class InstructionResult<T extends EntityDetail, I extends Instruction<T>> {

    private I next;
    private ResultState resultState;

    public InstructionResult() {
        this.next = null;
        this.resultState = ResultState.END;
    }

    public InstructionResult(I next) {
        this.next = next;
        this.resultState = ResultState.STANDARD;
    }

    public InstructionResult(I next, ResultState resultState) {
        if (!resultState.executes()) {
            assert next == null;
        }
        this.next = next;
        this.resultState = resultState;
    }

    public I next() {
        return next;
    }

    public ResultState getResult() {
        return resultState;
    }
}
