/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.skelril.openboss.condition;

import com.skelril.openboss.Boss;

public class Condition {

    private final Boss boss;

    public Condition(Boss boss) {
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }
}
