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

package com.skelril.OSBL;

import com.skelril.OSBL.block.LocalBlock;
import com.skelril.OSBL.entity.EntityDetail;
import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.instruction.*;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public abstract class BossDeclaration<T extends EntityDetail, A extends LocalEntity, K extends A, B extends LocalBlock, D> {

    private final InstructionDispatch<T, A, K, LocalControllable<T, A, K>> dispatch;

    /*
     * Binding System Variables
     */
    public final List<? extends BindInstruction<LocalControllable<T, A, K>>> bindInstructions = new ArrayList<>();
    public final List<? extends UnbindInstruction<LocalControllable<T, A, K>>> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
     */
    public final List<? extends PassiveInstruction<LocalControllable<T, A, K>>> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<? extends DamageInstruction<LocalControllable<T, A, K>, A>> damageInstructions = new ArrayList<>();
    public final List<? extends DamagedInstruction<LocalControllable<T, A, K>>> damagedInstructions = new ArrayList<>();

    public BossDeclaration(InstructionDispatch<T, A, K, LocalControllable<T, A, K>> dispatch) {
        this.dispatch = dispatch;
    }

    /*
     * Binding System
     */
    public void bind(LocalControllable<T, A, K> controllable) {
        silentBind(controllable);
        dispatch.bind(controllable).process(bindInstructions);
    }

    public abstract boolean matchesBind(K entity);

    public void unbind(LocalControllable<T, A, K> controllable) {
        silentUnbind(controllable);
        dispatch.unbind(controllable).process(unbindInstructions);
        dispatch.unbind(controllable).process(controllable.unbindInstructions);
    }

    public abstract LocalControllable<T, A, K> getBound(K entity);

    public abstract void silentBind(LocalControllable<T, A, K> controllable);
    public abstract void silentUnbind(LocalControllable<T, A, K> controllable);

    /*
     * Passive Effect System
     */
    public void process(LocalControllable<T, A, K> controllable) {
        dispatch.passive(controllable).process(passiveInstructions);
        dispatch.passive(controllable).process(controllable.passiveInstructions);
    }

    /*
     * Damage System Methods
     */
    public void damage(LocalControllable<T, A, K> attacker, A toHit, AttackDamage<D> damage) {
        dispatch.damage(attacker, toHit, damage).process(damageInstructions);
        dispatch.damage(attacker, toHit, damage).process(attacker.damageInstructions);
    }

    public void damaged(LocalControllable<T, A, K> defender, DamageSource<A, B> damager, AttackDamage<D> damage) {
        dispatch.damaged(defender, damager, damage).process(damagedInstructions);
        dispatch.damaged(defender, damager, damage).process(defender.damagedInstructions);
    }
}
