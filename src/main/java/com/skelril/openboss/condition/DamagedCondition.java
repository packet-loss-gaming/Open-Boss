/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.skelril.openboss.condition;

import com.skelril.openboss.Boss;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.util.Optional;

public class DamagedCondition extends Condition {

    private final DamageEntityEvent event;
    private final DamageSource attacker;

    public DamagedCondition(Boss boss, DamageEntityEvent event) {
        super(boss);
        this.event = event;
        this.attacker = event.getCause().first(DamageSource.class).orElse(null);
    }

    public Optional<DamageSource> getDamageSource() {
        return Optional.ofNullable(attacker);
    }

    public DamageEntityEvent getEvent() {
        return event;
    }
}
