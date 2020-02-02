package com.mistraltech.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class PStackUtilsTest {

  private final PStackUtils<Integer> utils = new PStackUtils<>(0);
  private final PStack<Integer> list = ConsPStack.singleton(1).plus(2);

  @Test
  @SuppressWarnings("ConstantConditions")
  void pad_WithNullList_ThrowsException() {
    Assertions.assertThatThrownBy(() -> utils.pad(null, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("list cannot be null");
  }

  @Test
  void pad_WithPaddedLengthLessThanListLength_ReturnsOriginalList() {
    assertThat(utils.pad(list, 1)).isEqualTo(list);
  }

  @Test
  void pad_WithPaddedLengthSameAsListLength_ReturnsOriginalList() {
    assertThat(utils.pad(list, 2)).isEqualTo(list);
  }

  @Test
  void pad_WithPaddedLengthGreaterThatListLength_ReturnsListWithPadding() {
    assertThat(utils.pad(list, 4)).isEqualTo(List.of(2, 1, 0, 0));
  }
}