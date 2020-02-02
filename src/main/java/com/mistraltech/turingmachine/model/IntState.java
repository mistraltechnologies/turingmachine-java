package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import com.mistraltech.utils.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntState implements State {

  private static final Map<Integer, IntState> stateMap = new HashMap<>();

  private final int value;

  private IntState(int value) {
    this.value = value;
  }

  public static List<IntState> createClosedRangeFromZeroTo(int maxState) {
    Preconditions.checkArgument(maxState >= 0, "max must be non-negative");

    return IntStream.rangeClosed(0, maxState)
        .mapToObj(IntState::getState)
        .collect(Collectors.toList());
  }

  public static IntState getState(int value) {
    return stateMap.computeIfAbsent(value, IntState::new);
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    IntState intState = (IntState) o;

    return value == intState.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public int compareTo(State o) {
    checkArgument(o != null, "Cannot compare to null");
    checkArgument(o instanceof IntState, "Cannot compare with type %s", o.getClass().toString());

    return value - ((IntState) o).value;
  }
}
