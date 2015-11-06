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
            if (!entity.equals(boss.getTargetEntity())) {
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
