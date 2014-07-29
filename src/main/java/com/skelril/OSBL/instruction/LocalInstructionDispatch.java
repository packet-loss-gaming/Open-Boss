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

import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

public interface LocalInstructionDispatch<T extends EntityDetail, A extends LocalEntity, K extends A, R extends LocalControllable<T, A, K>> {
    public InstructionProcessor<PassiveInstruction<R>, InstructionResult<PassiveInstruction<R>>> passive(R boss);

    public InstructionProcessor<DamageInstruction<R, A>, InstructionResult<DamageInstruction<R, A>>> damage(R boss, A toHit, AttackDamage damage);
    public InstructionProcessor<DamagedInstruction<R>, InstructionResult<DamagedInstruction<R>>> damaged(R boss, DamageSource hitBy, AttackDamage damage);
}
