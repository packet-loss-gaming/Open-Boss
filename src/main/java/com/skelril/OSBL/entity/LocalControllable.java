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

package com.skelril.OSBL.entity;

import com.skelril.OSBL.instruction.DamageInstruction;
import com.skelril.OSBL.instruction.DamagedInstruction;
import com.skelril.OSBL.instruction.PassiveInstruction;
import com.skelril.OSBL.instruction.UnbindInstruction;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalControllable<T extends EntityDetail> extends LocalEntity {

    private final T detail;

    /*
     * Binding System Variables
     */
    public final List<UnbindInstruction<T>> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
    */
    public final List<PassiveInstruction<T>> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<DamageInstruction<T>> damageInstructions = new ArrayList<>();
    public final List<DamagedInstruction<T>> damagedInstructions = new ArrayList<>();

    public LocalControllable(T detail) {
        this.detail = detail;
    }

    /*
     * Targeting System
     */
    public abstract void setTarget(LocalEntity target);
    public abstract LocalEntity getTarget();

    /*
     * Detail System
     */
    public T getDetail() {
        return detail;
    }
}
