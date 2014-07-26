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

package com.skelril.OSBL.bukkit.entity;

import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.interfaces.Minion;
import org.bukkit.entity.Damageable;

public class BukkitMinion<T extends EntityDetail> extends BukkitControllable<T> implements Minion {

    private BukkitBoss<T> boss;

    public BukkitMinion(Damageable minion, BukkitBoss<T> boss, T detail) {
        super(minion, detail);
        this.boss = boss;
    }

    @Override
    public LocalControllable<T> getOwningBoss() {
        return boss;
    }
}
