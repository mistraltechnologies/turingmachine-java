package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CharSymbolTest {

  private final CharSymbol aSymbol = CharSymbol.getSymbol('a');
  private final CharSymbol bSymbol = CharSymbol.getSymbol('b');

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_WithSameInstance_ReturnsTrue() {
    assertThat(aSymbol.equals(aSymbol)).isTrue();
  }

  @Test
  void equals_WithInstanceWithSameValue_ReturnsTrue() {
    assertThat(aSymbol.equals(CharSymbol.getSymbol('a'))).isTrue();
  }

  @Test
  void equals_WithInstanceWithDifferentValue_ReturnsFalse() {
    assertThat(aSymbol.equals(bSymbol)).isFalse();
  }

  @Test
  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  void equals_WithDifferentType_ReturnsFalse() {
    assertThat(aSymbol.equals("string")).isFalse();
  }

  @Test
  void hashCode_ForInstancesWithSameProperties_ReturnsSameResult() {
    assertThat(aSymbol.hashCode()).isEqualTo(CharSymbol.getSymbol('a').hashCode());
  }

  @Test
  void toString_ReturnsExpectedResult() {
    assertThat(aSymbol.toString()).isEqualTo("a");
    assertThat(bSymbol.toString()).isEqualTo("b");
  }

  @Test
  @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
  void compareTo_Null_ThrowsException() {
    assertThatThrownBy(() -> aSymbol.compareTo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot compare to null");
  }

  @Test
  void compareTo_HigherValuedInstance_ReturnsNegative() {
    assertThat(aSymbol.compareTo(bSymbol)).isLessThan(0);
  }

  @Test
  void compareTo_LowerValuedInstance_ReturnsPositive() {
    assertThat(bSymbol.compareTo(aSymbol)).isGreaterThan(0);
  }

  @Test
  void compareTo_SameValuedInstance_ReturnsZero() {
    assertThat(aSymbol.compareTo(CharSymbol.getSymbol('a'))).isEqualTo(0);
  }
}