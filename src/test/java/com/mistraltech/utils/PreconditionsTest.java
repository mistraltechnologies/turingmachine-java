package com.mistraltech.utils;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
class PreconditionsTest {

  @Test
  void checkArgument_WithFalseExpression_ThrowsIllegalArgumentException() {
    assertThatThrownBy(
        () -> Preconditions.checkArgument(false, "expression evaluated to %s", false))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("expression evaluated to false");
  }

  @Test
  void checkArgument_WithTrueExpression_DoesNotThrow() {
    assertThatCode(() -> Preconditions.checkArgument(true, "")).doesNotThrowAnyException();
  }

  @Test
  void checkState_WithFalseExpression_ThrowsIllegalArgumentException() {
    assertThatThrownBy(() -> Preconditions.checkState(false, "expression evaluated to %s", false))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("expression evaluated to false");
  }

  @Test
  void checkState_WithTrueExpression_DoesNotThrow() {
    assertThatCode(() -> Preconditions.checkState(true, "")).doesNotThrowAnyException();
  }
}