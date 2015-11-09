/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.skelril.openboss;

import org.spongepowered.api.entity.living.Living;

import java.util.List;
import java.util.Optional;

public class InstructionProcessor {
    public static <A, B extends Boss<? extends Living, ? extends EntityDetail>>
    void processInstructions(List<Instruction<A, B>> instructions, A input, B boss) {
        for (Instruction<A, B> instruction : instructions) {
            Optional<Instruction<A, B>> next = Optional.of(instruction);
            while (next.isPresent()) {
                next = next.get().apply(input, boss);
            }
        }
    }
}
