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
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public class DamageCondition extends Condition {

    private final DamageEntityEvent event;
    private final Entity attacked;

    public DamageCondition(Boss boss, Entity attacked, DamageEntityEvent event) {
        super(boss);
        this.event = event;
        this.attacked = attacked;
    }

    public DamageEntityEvent getEvent() {
        return event;
    }

    public Entity getAttacked() {
        return attacked;
    }
}
