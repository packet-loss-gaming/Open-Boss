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

package gg.packetloss.openboss.bukkit;

import gg.packetloss.openboss.BossDeclaration;
import gg.packetloss.openboss.bukkit.entity.BukkitControllable;
import gg.packetloss.openboss.bukkit.entity.BukkitEntity;
import gg.packetloss.openboss.bukkit.listener.BukkitListener;
import gg.packetloss.openboss.bukkit.listener.DefaultBukkitListener;
import gg.packetloss.openboss.bukkit.util.BukkitDamageSource;
import gg.packetloss.openboss.bukkit.util.BukkitUtil;
import gg.packetloss.openboss.entity.EntityDetail;
import gg.packetloss.openboss.entity.LocalControllable;
import gg.packetloss.openboss.entity.LocalEntity;
import gg.packetloss.openboss.instruction.InstructionDispatch;
import gg.packetloss.openboss.util.AttackDamage;
import gg.packetloss.openboss.util.DamageSource;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class BukkitBossDeclaration<T extends EntityDetail> extends BossDeclaration<T> {

    /*
     * Declaring Bukkit plugin
     */
    private final Plugin declarer;

    /*
     * Controlled list
     */
    public final Map<UUID, LocalControllable<T>> controlled = new HashMap<>();

    public BukkitBossDeclaration(Plugin declarer, InstructionDispatch<T> dispatch) {
        this(declarer, dispatch, null);
    }

    public BukkitBossDeclaration(Plugin declarer, InstructionDispatch<T> dispatch, BukkitListener listener) {
        super(dispatch);

        assert declarer != null;

        this.declarer = declarer;

        if (listener == null) {
            listener = new DefaultBukkitListener<>(declarer, this);
        }
        registerListener(listener);
    }

    private void registerListener(BukkitListener listener) {
        declarer.getServer().getPluginManager().registerEvents(listener, declarer);
    }

    public Map<UUID, LocalControllable<T>> getControlled() {
        return Collections.unmodifiableMap(controlled);
    }

    public void cleanup() {
        Iterator<Map.Entry<UUID, LocalControllable<T>>> it = controlled.entrySet().iterator();
        while (it.hasNext()) {
            if (!it.next().getValue().isValid()) {
                it.remove();
            }
        }
    }

    @Override
    public abstract boolean matchesBind(LocalEntity entity);

    @Override
    public LocalControllable<T> getBound(LocalEntity entity) {
        assert entity != null && entity instanceof BukkitEntity;
        return controlled.get(BukkitUtil.grabUUID(entity));
    }

    @Override
    public void silentBind(LocalControllable<T> controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        controlled.put(BukkitUtil.grabUUID(controllable), controllable);
    }

    @Override
    public void silentUnbind(LocalControllable<T> controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        controlled.remove(BukkitUtil.grabUUID(controllable));
    }

    @Override
    public void process(LocalControllable<T> controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        super.process(controllable);
    }

    @Override
    public void damage(LocalControllable<T> attacker, LocalEntity toHit, AttackDamage damage) {
        assert attacker != null && attacker instanceof BukkitControllable;
        assert toHit != null && toHit instanceof BukkitEntity;
        super.damage(attacker, toHit, damage);
    }

    @Override
    public void damaged(LocalControllable<T> defender, DamageSource damager, AttackDamage damage) {
        assert defender != null && defender instanceof BukkitControllable;
        assert damager != null && damager instanceof BukkitDamageSource;
        super.damaged(defender, damager, damage);
    }
}
