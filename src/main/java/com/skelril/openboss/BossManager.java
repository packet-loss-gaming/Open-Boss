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
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.skelril.openboss.InstructionProcessor.processInstructions;

public class BossManager<T extends Living, K extends EntityDetail> extends ProcessedComponent<T, K> {

    private Map<UUID, Boss<T, K>> bosses = new HashMap<>();

    public Optional<Boss<T, K>> lookup(UUID entity) {
        return Optional.ofNullable(bosses.get(entity));
    }

    public Optional<Boss<T, K>> updateLookup(T entity) {
        Optional<Boss<T, K>> optBoss = lookup(entity.getUniqueId());
        if (optBoss.isPresent()) {
            Boss<T, K> boss = optBoss.get();
            if (!entity.equals(boss.getTargetEntity().orElse(null))) {
                boss.setTargetEntity(entity);
            }
        }
        return optBoss;
    }

    public void bind(Boss<T, K> boss) {
        silentBind(boss);
        BindCondition bindCondition = new BindCondition(boss);
        processInstructions(this.getBindProcessor(), bindCondition, boss);
        processInstructions(boss.getBindProcessor(), bindCondition, boss);
    }

    public void silentBind(Boss<T, K> boss) {
        bosses.put(boss.getEntityID(), boss);
    }

    public void unbind(Boss<T, K> boss) {
        silentUnbind(boss);
        UnbindCondition unbindCondition = new UnbindCondition(boss);
        processInstructions(this.getUnbindProcessor(), unbindCondition, boss);
        processInstructions(boss.getUnbindProcessor(), unbindCondition, boss);
    }

    public void silentUnbind(Boss<T, K> boss) {
        bosses.remove(boss.getEntityID());
    }

    public void callDamage(Boss<T, K> boss, Entity target, DamageEntityEvent event) {
        DamageCondition damageCondition = new DamageCondition(boss, target, event);
        processInstructions(this.getDamageProcessor(), damageCondition, boss);
        processInstructions(boss.getDamageProcessor(), damageCondition, boss);
    }

    public void callDamaged(Boss<T, K> boss, DamageEntityEvent event) {
        DamagedCondition damagedCondition = new DamagedCondition(boss, event);
        processInstructions(this.getDamagedProcessor(), damagedCondition, boss);
        processInstructions(boss.getDamagedProcessor(), damagedCondition, boss);
    }
}
