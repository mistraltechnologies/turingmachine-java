package com.mistraltech.turingmachine.model;

import java.util.Optional;
import java.util.function.BiFunction;

public interface Program extends BiFunction<State, Symbol, Optional<Action>> {

}
