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

package com.skelril.openboss;

import com.skelril.openboss.condition.BindCondition;
import com.skelril.openboss.condition.DamageCondition;
import com.skelril.openboss.condition.DamagedCondition;
import com.skelril.openboss.condition.UnbindCondition;
import org.spongepowered.api.entity.living.Living;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessedComponent<T extends Living, K extends EntityDetail> {
    private final List<Instruction<BindCondition, Boss<T, K>>> bindProcessor = new ArrayList<>();
    private final List<Instruction<DamageCondition, Boss<T, K>>> damageProcessor = new ArrayList<>();
    private final List<Instruction<DamagedCondition, Boss<T, K>>> damagedProcessor = new ArrayList<>();
    private final List<Instruction<UnbindCondition, Boss<T, K>>> unbindProcessor = new ArrayList<>();

    public List<Instruction<BindCondition, Boss<T, K>>> getBindProcessor() {
        return bindProcessor;
    }

    public List<Instruction<DamageCondition, Boss<T, K>>> getDamageProcessor() {
        return damageProcessor;
    }

    public List<Instruction<DamagedCondition, Boss<T, K>>> getDamagedProcessor() {
        return damagedProcessor;
    }

    public List<Instruction<UnbindCondition, Boss<T, K>>> getUnbindProcessor() {
        return unbindProcessor;
    }
}
