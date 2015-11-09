/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.skelril.openboss;

import java.util.Optional;

public interface Instruction<A, B> {
    Optional<Instruction<A, B>> apply(A a, B b);
}
