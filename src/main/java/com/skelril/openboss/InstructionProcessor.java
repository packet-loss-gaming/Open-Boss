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