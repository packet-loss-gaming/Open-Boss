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

package gg.packetloss.openboss.entity;

import gg.packetloss.openboss.instruction.DamageInstruction;
import gg.packetloss.openboss.instruction.DamagedInstruction;
import gg.packetloss.openboss.instruction.PassiveInstruction;
import gg.packetloss.openboss.instruction.UnbindInstruction;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class LocalControllable<T extends EntityDetail> extends LocalEntity {
    private final T detail;
    private final Map<UUID, Double> damagerMap = new HashMap<>();

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

    public void damaged(@Nullable UUID damager, double damage) {
        damagerMap.merge(damager, damage, Double::sum);
    }

    public List<UUID> getDamagers() {
        return damagerMap.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Optional<Double> getDamage(UUID damager) {
        if (damagerMap.containsKey(damager)) {
            return Optional.of(damagerMap.get(damager));
        }

        return Optional.empty();
    }
}
