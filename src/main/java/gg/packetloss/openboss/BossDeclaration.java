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

package gg.packetloss.openboss;

import gg.packetloss.openboss.entity.EntityDetail;
import gg.packetloss.openboss.entity.LocalControllable;
import gg.packetloss.openboss.entity.LocalEntity;
import gg.packetloss.openboss.instruction.*;
import gg.packetloss.openboss.util.AttackDamage;
import gg.packetloss.openboss.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public abstract class BossDeclaration<T extends EntityDetail> {

    private final InstructionDispatch<T> dispatch;

    /*
     * Binding System Variables
     */
    public final List<BindInstruction<T>> bindInstructions = new ArrayList<>();
    public final List<UnbindInstruction<T>> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
     */
    public final List<PassiveInstruction<T>> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<DamageInstruction<T>> damageInstructions = new ArrayList<>();
    public final List<DamagedInstruction<T>> damagedInstructions = new ArrayList<>();

    public BossDeclaration(InstructionDispatch<T> dispatch) {
        this.dispatch = dispatch;
    }

    /*
     * Binding System
     */
    public void bind(LocalControllable<T> controllable) {
        silentBind(controllable);
        dispatch.bind(controllable).process(bindInstructions);
    }

    public abstract boolean matchesBind(LocalEntity entity);

    public LocalControllable<T> tryRebind(LocalEntity entity) {
        return null;
    }

    public void unbind(LocalControllable<T> controllable) {
        silentUnbind(controllable);
        dispatch.unbind(controllable).process(unbindInstructions);
        dispatch.unbind(controllable).process(controllable.unbindInstructions);
    }

    public abstract LocalControllable getBound(LocalEntity entity);

    public abstract void silentBind(LocalControllable<T> controllable);
    public abstract void silentUnbind(LocalControllable<T> controllable);

    /*
     * Passive Effect System
     */
    public void process(LocalControllable<T> controllable) {
        dispatch.passive(controllable).process(passiveInstructions);
        dispatch.passive(controllable).process(controllable.passiveInstructions);
    }

    /*
     * Damage System Methods
     */
    public void damage(LocalControllable<T> attacker, LocalEntity toHit, AttackDamage damage) {
        dispatch.damage(attacker, toHit, damage).process(damageInstructions);
        dispatch.damage(attacker, toHit, damage).process(attacker.damageInstructions);
    }

    public void damaged(LocalControllable<T> defender, DamageSource damager, AttackDamage damage) {
        LocalEntity damagingEntity = damager.getDamagingEntity();
        if (damagingEntity != null) {
            defender.damaged(damagingEntity.getUUID(), damage.getDamage());
        }

        dispatch.damaged(defender, damager, damage).process(damagedInstructions);
        dispatch.damaged(defender, damager, damage).process(defender.damagedInstructions);
    }
}
