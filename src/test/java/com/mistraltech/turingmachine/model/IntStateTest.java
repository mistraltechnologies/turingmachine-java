package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
class IntStateTest {

  private final IntState intState1 = IntState.getState(1);
  private final IntState intState2 = IntState.getState(2);

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_WithSameInstance_ReturnsTrue() {
    assertThat(intState1.equals(intState1)).isTrue();
  }

  @Test
  void equals_WithInstanceWithSameProperties_ReturnsTrue() {
    assertThat(intState1.equals(IntState.getState(1))).isTrue();
  }

  @Test
  void equals_WithNull_ReturnsFalse() {
    assertThat(intState1.equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  void equals_WithDifferentType_ReturnsFalse() {
    assertThat(intState1.equals("string")).isFalse();
  }

  @Test
  void equals_WithInstanceWithDifferentValue_ReturnsFalse() {
    assertThat(intState1.equals(intState2)).isFalse();
  }

  @Test
  void hashCode_ForInstancesWithSamePropertyValues_ReturnsSameResult() {
    assertThat(intState1.hashCode()).isEqualTo(IntState.getState(1).hashCode());
  }

  @Test
  void toString_ReturnsExpectedResult() {
    assertThat(intState1.toString()).isEqualTo("1");
    assertThat(intState2.toString()).isEqualTo("2");
  }

  @Test
  @SuppressWarnings("ResultOfMethodCallIgnored")
  void compareTo_Null_ThrowsException() {
    assertThatThrownBy(() -> intState1.compareTo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot compare to null");
  }

  @Test
  void compareTo_HigherValuedInstance_ReturnsNegative() {
    assertThat(intState1.compareTo(intState2)).isLessThan(0);
  }

  @Test
  void compareTo_LowerValuedInstance_ReturnsPositive() {
    assertThat(intState2.compareTo(intState1)).isGreaterThan(0);
  }

  @Test
  void compareTo_SameValuedInstance_ReturnsZero() {
    assertThat(intState1.compareTo(IntState.getState(1))).isEqualTo(0);
  }

  @Test
  void createClosedRangeFromZeroTo_WithNegativeMax_ThrowsException() {
    assertThatThrownBy(() -> IntState.createClosedRangeFromZeroTo(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("max must be non-negative");
  }

  @Test
  void createClosedRangeFromZeroTo_WithPositiveMax_CreatesStatesFromZeroToMaxInclusive() {
    List<IntState> intStates = IntState.createClosedRangeFromZeroTo(3);

    assertThat(intStates)
        .containsExactly(IntState.getState(0), IntState.getState(1), IntState.getState(2), IntState.getState(3));
  }

  @Test
  void createClosedRangeFromZeroTo_WithZeroMax_CreatesStatesContainingZero() {
    List<IntState> intStates = IntState.createClosedRangeFromZeroTo(0);

    assertThat(intStates).containsExactly(IntState.getState(0));
  }
}