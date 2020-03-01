package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import com.mistraltech.utils.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A State implementation with states represented by Integers. IntState cannot be constructed directly. Use one of the
 * static factory methods to construct and retrieve IntState instances.
 */
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
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    IntState intState = (IntState) other;

    return value == intState.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public int compareTo(State other) {
    checkArgument(other != null, "Cannot compare to null");
    checkArgument(other instanceof IntState, "Cannot compare with type %s", other.getClass().toString());

    return value - ((IntState) other).value;
  }
}
