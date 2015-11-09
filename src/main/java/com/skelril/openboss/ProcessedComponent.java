/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
