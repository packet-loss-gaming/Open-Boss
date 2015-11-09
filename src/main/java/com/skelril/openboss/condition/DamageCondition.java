/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
