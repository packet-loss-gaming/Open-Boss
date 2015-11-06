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

package com.skelril.openboss.condition;

import com.skelril.openboss.Boss;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.util.Optional;

public class DamagedCondition extends Condition {

    private final DamageEntityEvent event;
    private final Optional<Living> optAttacker;

    public DamagedCondition(Boss boss, DamageEntityEvent event) {
        super(boss);
        this.event = event;
        this.optAttacker = event.getCause().first(Living.class);
    }

    public Optional<Living> getAttacker() {
        return optAttacker;
    }

    public DamageEntityEvent getEvent() {
        return event;
    }
}
