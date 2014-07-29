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

package com.skelril.OSBL.bukkit;

import com.skelril.OSBL.BossDeclaration;
import com.skelril.OSBL.bukkit.block.BukkitBlock;
import com.skelril.OSBL.bukkit.entity.BukkitControllable;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.bukkit.listener.BukkitListener;
import com.skelril.OSBL.bukkit.listener.DefaultBukkitListener;
import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.instruction.InstructionDispatch;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class BukkitBossDeclaration<T extends EntityDetail, K extends Entity> extends BossDeclaration<T, BukkitEntity, BukkitEntity<K>, BukkitBlock, EntityDamageEvent> {

    /*
     * Declaring Bukkit plugin
     */
    private final Plugin declarer;

    /*
     * Controlled list
     */
    public final Map<UUID, BukkitControllable<T, K>> controlled = new HashMap<>();

    public BukkitBossDeclaration(Plugin declarer,
                                 InstructionDispatch<
                                         T,
                                         BukkitEntity,
                                         BukkitEntity<K>,
                                         LocalControllable<
                                                 T,
                                                 BukkitEntity,
                                                 BukkitEntity<K>
                                         >
                                 >
                                 dispatch,
                                 Class<K> type) {
        this(declarer, dispatch, type, null);
    }

    public BukkitBossDeclaration(Plugin declarer,
                                 InstructionDispatch<
                                         T,
                                         BukkitEntity,
                                         BukkitEntity<K>,
                                         LocalControllable<
                                                 T,
                                                 BukkitEntity,
                                                 BukkitEntity<K>
                                         >
                                 >
                                 dispatch,
                                 Class<K> type,
                                 BukkitListener listener) {
        super(dispatch);

        assert declarer != null;

        this.declarer = declarer;

        if (listener == null) {
            listener = new DefaultBukkitListener<>(this, type);
        }
        registerListener(listener);
    }

    private void registerListener(BukkitListener listener) {
        declarer.getServer().getPluginManager().registerEvents(listener, declarer);
    }

    public Map<UUID, LocalControllable<T, BukkitEntity, BukkitEntity<K>>> getControlled() {
        return Collections.unmodifiableMap(controlled);
    }

    public void cleanup() {
        Iterator<Map.Entry<UUID, BukkitControllable<T, K>>> it = controlled.entrySet().iterator();
        while (it.hasNext()) {
            if (!it.next().getValue().isValid()) {
                it.remove();
            }
        }
    }

    @Override
    public abstract boolean matchesBind(BukkitEntity<K> entity);

    @Override
    public BukkitControllable<T, K> getBound(BukkitEntity entity) {
        assert entity != null;
        return controlled.get(entity.getBukkitEntity().getUniqueId());
    }

    @Override
    public void silentBind(LocalControllable<T, BukkitEntity, BukkitEntity<K>> controllable) {
        assert controllable != null && controllable instanceof BukkitControllable;
        controlled.put(controllable.getLocalEntity().getBukkitEntity().getUniqueId(), (BukkitControllable<T, K>) controllable);
    }

    @Override
    public void silentUnbind(LocalControllable<T, BukkitEntity, BukkitEntity<K>> controllable) {
        assert controllable != null;
        controlled.remove(controllable.getLocalEntity().getBukkitEntity().getUniqueId());
    }
}
