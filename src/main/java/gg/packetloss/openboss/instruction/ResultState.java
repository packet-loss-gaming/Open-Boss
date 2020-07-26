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

public enum ResultState {
    STANDARD,
    TERMINAL(true, true),
    END(false, true);

    private final boolean executes;
    private final boolean terminal;

    private ResultState() {
        executes = true;
        terminal = false;
    }

    private ResultState(boolean executes, boolean terminal) {
        this.executes = executes;
        this.terminal = terminal;
    }

    public boolean executes() {
        return executes;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
