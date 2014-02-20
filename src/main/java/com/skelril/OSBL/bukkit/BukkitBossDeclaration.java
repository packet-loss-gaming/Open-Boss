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

import com.skelril.OSBL.bukkit.util.BukkitAttackDamage;
import com.skelril.OSBL.bukkit.util.BukkitDamageSource;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;
import com.skelril.OSBL.bukkit.entity.BukkitBoss;
import com.skelril.OSBL.bukkit.entity.BukkitEntity;
import com.skelril.OSBL.entity.Boss;
import com.skelril.OSBL.BossDeclaration;
import com.skelril.OSBL.entity.LocalEntity;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BukkitBossDeclaration extends BossDeclaration {

    /*
     * Declaring Bukkit plugin
     */
    private final Plugin declarer;

    /*
     * Boss list
     */
    protected final Map<UUID, BukkitBoss> bosses;

    public BukkitBossDeclaration(Plugin declarer) {
        super();

        assert declarer != null;

        this.declarer = declarer;
        this.bosses = new HashMap<>();

        declarer.getServer().getPluginManager().registerEvents(new BukkitListener(this), declarer);
    }

    @Override
    public void bind(Boss boss) {
        assert boss != null && boss instanceof BukkitBoss;
        bosses.put(((BukkitEntity) boss.getLocalEntity()).getBukkitEntity().getUniqueId(), (BukkitBoss) boss);
    }

    @Override
    public void unbind(Boss boss) {
        assert boss != null && boss instanceof BukkitBoss;
        bosses.remove(((BukkitEntity) boss.getLocalEntity()).getBukkitEntity().getUniqueId());
    }

    @Override
    public void damage(Boss attacker, LocalEntity toHit, AttackDamage damage) {
        assert attacker != null && attacker instanceof BukkitBoss;
        assert toHit != null && toHit instanceof BukkitEntity;
        super.damage(attacker, toHit, damage);
        attacker.damage(toHit, damage);
    }

    @Override
    public void damaged(Boss defender, DamageSource damager, double damage) {
        assert defender != null && defender instanceof BukkitBoss;
        assert damager != null && damager instanceof BukkitDamageSource;
        super.damaged(defender, damager, damage);
        defender.damaged(damager, damage);
    }
}
