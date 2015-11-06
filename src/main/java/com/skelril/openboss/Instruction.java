package com.skelril.openboss;

import java.util.Optional;

public interface Instruction<A, B> {
    Optional<Instruction<A, B>> apply(A a, B b);
}
