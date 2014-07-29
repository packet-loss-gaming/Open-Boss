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

package com.skelril.OSBL.instruction;

import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;

public abstract class DamageInstruction<R, A extends LocalEntity> implements Instruction<InstructionResult<DamageInstruction<R, A>>, R> {
    private A toHit;
    private AttackDamage damage;

    protected DamageInstruction<R, A> supply(A toHit, AttackDamage damage) {
        this.toHit = toHit;
        this.damage = damage;
        return this;
    }

    public A getToHit() {
        return toHit;
    }

    public AttackDamage getDamage() {
        return damage;
    }
}