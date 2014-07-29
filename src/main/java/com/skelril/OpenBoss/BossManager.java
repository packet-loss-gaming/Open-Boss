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

package com.skelril.OpenBoss;

import com.skelril.OpenBoss.condition.BindCondition;
import com.skelril.OpenBoss.condition.DamageCondition;
import com.skelril.OpenBoss.condition.DamagedCondition;
import com.skelril.OpenBoss.condition.UnbindCondition;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossManager extends ProcessedComponent {

    private Map<UUID, Boss> bosses = new HashMap<>();

    public Boss lookup(UUID entity) {
        return bosses.get(entity);
    }

    public void bind(Boss boss) {
        silentBind(boss);
        getBindProcessor().processInstructions(new BindCondition(boss));
    }

    public void silentBind(Boss boss) {
        bosses.put(boss.getEntity().getUniqueId(), boss);
    }

    public void unbind(Boss boss) {
        silentUnbind(boss);
        getUnbindProcessor().processInstructions(new UnbindCondition(boss));
    }

    public void silentUnbind(Boss boss) {
        bosses.remove(boss.getEntity().getUniqueId());
    }

    public void callDamage(Boss boss, EntityDamageByEntityEvent event) {
        getDamageProcessor().processInstructions(new DamageCondition(boss, event));
    }

    public void callDamaged(Boss boss, EntityDamageEvent event) {
        getDamagedProcessor().processInstructions(new DamagedCondition(boss, event));
    }
}
