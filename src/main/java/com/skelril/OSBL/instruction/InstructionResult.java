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
    private boolean die;

    public InstructionResult() {
        next = null;
        die = true;
    }

    public InstructionResult(Instruction next) {
        this.next = next;
        this.die = false;
    }

    public InstructionResult(Instruction next, boolean die) {
        this.next = next;
        this.die = die;
    }

    public Instruction next() {
        return next;
    }

    public boolean die() {
        return die;
    }
}
