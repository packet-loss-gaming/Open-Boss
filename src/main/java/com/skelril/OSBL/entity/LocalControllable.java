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

package com.skelril.OSBL.entity;

import com.skelril.OSBL.instruction.*;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalControllable<T extends EntityDetail, A extends LocalEntity, K extends A> implements LocalEntity<K> {

    private final T detail;

    /*
     * Binding System Variables
     */
    public final List<? extends UnbindInstruction<LocalControllable<T, A, K>>> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
    */
    public final List<? extends PassiveInstruction<LocalControllable<T, A, K>>> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<? extends DamageInstruction<LocalControllable<T, A, K>, A>> damageInstructions = new ArrayList<>();
    public final List<? extends DamagedInstruction<LocalControllable<T, A, K>>> damagedInstructions = new ArrayList<>();

    public LocalControllable(T detail) {
        this.detail = detail;
    }

    /*
     * Targeting System
     */
    public abstract void setTarget(A target);
    public abstract A getTarget();

    /*
     * Detail System
     */
    public T getDetail() {
        return detail;
    }
}
