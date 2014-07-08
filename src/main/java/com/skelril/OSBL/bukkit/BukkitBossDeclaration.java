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

package com.skelril.OSBL.bukkit;

import com.skelril.OSBL.BossDeclaration;
import com.skelril.OSBL.bukkit.entity.BukkitControllable;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.bukkit.listener.BukkitListener;
import com.skelril.OSBL.bukkit.listener.DefaultBukkitListener;
import com.skelril.OSBL.bukkit.util.BukkitDamageSource;
import com.skelril.OSBL.bukkit.util.BukkitUtil;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class BukkitBossDeclaration extends BossDeclaration {

    /*
     * Declaring Bukkit plugin
     */
    private final Plugin declarer;

    /*
     * Controlled list
     */
    public final Map<UUID, BukkitControllable> controlled = new HashMap<>();

    public BukkitBossDeclaration(Plugin declarer) {
        this(declarer, null);
    }

    public BukkitBossDeclaration(Plugin declarer, BukkitListener listener) {
        super();

        assert declarer != null;

        this.declarer = declarer;

        if (listener == null) {
            listener = new DefaultBukkitListener(this);
        }
        registerListener(listener);
    }

    private void registerListener(BukkitListener listener) {
        declarer.getServer().getPluginManager().registerEvents(listener, declarer);
    }

    public Map<UUID, BukkitControllable> getControlled() {
        return Collections.unmodifiableMap(controlled);
    }

    public void cleanup() {
        Iterator<Map.Entry<UUID, BukkitControllable>> it = controlled.entrySet().iterator();
        while (it.hasNext()) {
            if (!it.next().getValue().isValid()) {
                it.remove();
            }
        }
    }

    @Override
    public abstract boolean matchesBind(LocalEntity entity);

    @Override
    public LocalControllable getBound(LocalEntity entity) {
        assert entity != null && entity instanceof BukkitEntity;
        return controlled.get(BukkitUtil.grabUUID(entity));
    }

    @Override
    public void silentBind(LocalControllable controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        controlled.put(BukkitUtil.grabUUID(controllable), (BukkitControllable) controllable);
    }

    @Override
    public void silentUnbind(LocalControllable controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        controlled.remove(BukkitUtil.grabUUID(controllable));
    }

    @Override
    public void processGeneralAndPersonalEffects(LocalControllable controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        super.processGeneralAndPersonalEffects(controllable);
    }

    @Override
    public void processAllPersonalEffects() {
        for (LocalControllable controllable : controlled.values()) {
            controllable.processEffects();
        }
    }

    @Override
    public void damage(LocalControllable attacker, LocalEntity toHit, AttackDamage damage) {
        assert attacker != null && attacker instanceof BukkitControllable;
        assert toHit != null && toHit instanceof BukkitEntity;
        super.damage(attacker, toHit, damage);
    }

    @Override
    public void damaged(LocalControllable defender, DamageSource damager, AttackDamage damage) {
        assert defender != null && defender instanceof BukkitControllable;
        assert damager != null && damager instanceof BukkitDamageSource;
        super.damaged(defender, damager, damage);
    }
}
