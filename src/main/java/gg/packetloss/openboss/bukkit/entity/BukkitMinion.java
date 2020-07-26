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

package gg.packetloss.openboss.bukkit.entity;

import gg.packetloss.openboss.bukkit.util.BukkitUtil;
import gg.packetloss.openboss.entity.EntityDetail;
import gg.packetloss.openboss.entity.LocalControllable;
import gg.packetloss.openboss.entity.interfaces.Minion;
import org.bukkit.entity.Damageable;

public class BukkitMinion<T extends EntityDetail> extends BukkitControllable<T> implements Minion {

    private BukkitBoss<T> boss;

    public BukkitMinion(Damageable minion, BukkitBoss<T> boss, T detail) {
        super(minion, detail);
        this.boss = boss;
    }

    @Override
    public LocalControllable getOwningBoss() {
        return boss;
    }

    @Override
    public boolean equals(Object entity) {
        return entity != null && controlled.equals(BukkitUtil.getBukkitEntity(entity));
    }
}
