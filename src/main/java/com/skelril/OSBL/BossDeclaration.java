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

package com.skelril.OSBL;

import com.skelril.OSBL.entity.LocalControllable;
import com.skelril.OSBL.entity.LocalEntity;
import com.skelril.OSBL.instruction.*;
import com.skelril.OSBL.util.AttackDamage;
import com.skelril.OSBL.util.DamageSource;

import java.util.ArrayList;
import java.util.List;

public abstract class BossDeclaration {

    private final InstructionDispatch dispatch;

    /*
     * Binding System Variables
     */
    public final List<BindInstruction> bindInstructions = new ArrayList<>();
    public final List<UnbindInstruction> unbindInstructions = new ArrayList<>();

    /*
     * Passive Effect System Variables
     */
    public final List<PassiveInstruction> passiveInstructions = new ArrayList<>();

    /*
     * Damage System Variables
     */
    public final List<DamageInstruction> damageInstructions = new ArrayList<>();
    public final List<DamagedInstruction> damagedInstructions = new ArrayList<>();

    public BossDeclaration(InstructionDispatch dispatch) {
        this.dispatch = dispatch;
    }

    /*
     * Binding System
     */
    public void bind(LocalControllable controllable) {
        silentBind(controllable);
        dispatch.bind(controllable).process(bindInstructions);
    }

    public abstract boolean matchesBind(LocalEntity entity);

    public void unbind(LocalControllable controllable) {
        silentUnbind(controllable);
        dispatch.unbind(controllable).process(unbindInstructions);
        dispatch.unbind(controllable).process(controllable.unbindInstructions);
    }

    public abstract LocalControllable getBound(LocalEntity entity);

    public abstract void silentBind(LocalControllable controllable);
    public abstract void silentUnbind(LocalControllable controllable);

    /*
     * Passive Effect System
     */
    public void process(LocalControllable controllable) {
        dispatch.passive(controllable).process(passiveInstructions);
        dispatch.passive(controllable).process(controllable.passiveInstructions);
    }

    /*
     * Damage System Methods
     */
    public void damage(LocalControllable attacker, LocalEntity toHit, AttackDamage damage) {
        dispatch.damage(attacker, toHit, damage).process(damageInstructions);
        dispatch.damage(attacker, toHit, damage).process(attacker.damageInstructions);
    }

    public void damaged(LocalControllable defender, DamageSource damager, AttackDamage damage) {
        dispatch.damaged(defender, damager, damage).process(damagedInstructions);
        dispatch.damaged(defender, damager, damage).process(defender.damagedInstructions);
    }
}
