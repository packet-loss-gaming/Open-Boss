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
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DistributedBossManager extends ProcessedComponent {

    private Set<SlaveBossManager> managers = new HashSet<>();

    public SlaveBossManager obtainManager() {
        SlaveBossManager manager = new SlaveBossManager();
        managers.add(manager);
        return manager;
    }

    public void forgetManager(SlaveBossManager manager) {
        managers.remove(manager);
    }

    public Set<SlaveBossManager> getManagers() {
        return Collections.unmodifiableSet(managers);
    }

    public Boss lookup(UUID entity, SlaveBossManager manager) {
        return manager.lookup(entity);
    }

    public Boss updateLookup(LivingEntity entity, SlaveBossManager manager) {
        Boss boss = lookup(entity.getUniqueId(), manager);
        if (boss != null && !entity.equals(boss.getEntity())) {
            boss.setEntity(entity);
        }
        return boss;
    }

    public boolean bind(Boss boss, SlaveBossManager manager) {
        if (silentBind(boss, manager)) {
            BindCondition bindCondition = new BindCondition(boss);
            this.getBindProcessor().processInstructions(bindCondition);
            boss.getBindProcessor().processInstructions(bindCondition);
            return true;
        }
        return false;
    }

    public boolean silentBind(Boss boss, SlaveBossManager manager) {
        for (SlaveBossManager slave : managers) {
            if (slave.lookup(boss.getEntityID()) != null) {
                return false;
            }
        }
        manager.silentBind(boss);
        return true;
    }

    public boolean unbind(Boss boss, SlaveBossManager manager) {
        UnbindCondition unbindCondition = new UnbindCondition(boss);
        this.getUnbindProcessor().processInstructions(unbindCondition);
        boss.getUnbindProcessor().processInstructions(unbindCondition);
        return silentUnbind(boss, manager);
    }

    public boolean silentUnbind(Boss boss, SlaveBossManager manager) {
        return manager.silentUnbind(boss);
    }

    public void callDamage(Boss boss, EntityDamageByEntityEvent event) {
        DamageCondition damageCondition = new DamageCondition(boss, event);
        this.getDamageProcessor().processInstructions(damageCondition);
        boss.getDamageProcessor().processInstructions(damageCondition);
    }

    public void callDamaged(Boss boss, EntityDamageEvent event) {
        DamagedCondition damagedCondition = new DamagedCondition(boss, event);
        this.getDamagedProcessor().processInstructions(damagedCondition);
        boss.getDamagedProcessor().processInstructions(damagedCondition);
    }
}
