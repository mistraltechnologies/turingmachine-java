package com.mistraltech.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
class PersistentStackImplTest {

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_ComparedWithSameStack_ReturnsTrue() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.equals(s)).isTrue();
  }

  @Test
  void equals_WithEmptyStackComparedWithNonEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = PersistentStackImpl.empty();

    assertThat(s.equals(PersistentStackImpl.singleton(1))).isFalse();
  }

  @Test
  void equals_WithNonEmptyStackComparedWithEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.equals(PersistentStackImpl.empty())).isFalse();
  }

  @Test
  void equals_WithSingleElementStackComparedWithSimilarSingleElementStack_ReturnsTrue() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.equals(PersistentStackImpl.singleton(1))).isTrue();
  }

  @Test
  void equals_WithSingleElementStackComparedWithDifferentSingleElementStack_ReturnsFalse() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.equals(PersistentStackImpl.singleton(2))).isFalse();
  }

  @Test
  void equals_WithEmptyStackComparedToEmptyStack_ReturnsTrue() {
    PersistentStackImpl<Integer> s = PersistentStackImpl.empty();

    assertThat(s.equals(PersistentStackImpl.empty())).isTrue();
  }

  @Test
  void equals_WithEmptyStackComparedToNull_ReturnsFalse() {
    PersistentStackImpl<Integer> s = PersistentStackImpl.empty();

    assertThat(s.equals(null)).isFalse();
  }

  @Test
  void singleton_WithNullParameter_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to construct with null item");
  }

  @Test
  void from_WithNullParameter_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.from(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to construct with null list");
  }

  @Test
  void from_WithListOfElements_ReturnsStackWithElementsInSameOrder() {
    PersistentStackImpl<Integer> stack = PersistentStackImpl.from(List.of(1, 2, 3));

    assertThat(stack).isEqualTo(PersistentStackImpl.singleton(3).push(2).push(1));
  }

  @Test
  void toString_WithEmptyStack_ReturnsExpectedText() {
    PersistentStack<Integer> s = PersistentStackImpl.empty();

    assertThat(s.toString()).isEqualTo("PersistentStackImpl[head=null, tail=null]");
  }

  @Test
  void toString_WithStackWithOneElement_ReturnsExpectedText() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.toString())
        .isEqualTo("PersistentStackImpl[head=1, tail=PersistentStackImpl[head=null, tail=null]]");
  }

  @Test
  void pop_WithEmptyStack_ThrowsIllegalStateException() {
    PersistentStack<Integer> s = PersistentStackImpl.empty();

    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(s::pop)
        .withMessage("Attempt to pop empty stack");
  }

  @Test
  void pop_WithSingleElementStack_ReturnsEmptyStack() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.pop()).isEqualTo(PersistentStackImpl.empty());
  }

  @Test
  void pop_WithNonEmptyStack_DoesNotModifyOriginalStack() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);
    s.pop();

    assertThat(s).isEqualTo(PersistentStackImpl.singleton(1));
  }

  @Test
  void peek_WithEmptyStack_ReturnsEmpty() {
    PersistentStack<Integer> s = PersistentStackImpl.empty();

    assertThat(s.peek()).isEmpty();
  }

  @Test
  void peek_WithNonEmptyStack_ReturnsLastElement() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.peek()).isEqualTo(Optional.of(1));
  }

  @Test
  void read_WithEmptyStack_ThrowsIllegalStateException() {
    PersistentStack<Integer> s = PersistentStackImpl.empty();

    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(s::read)
        .withMessage("Attempt to read from empty stack");
  }

  @Test
  void read_WithNonEmptyStack_ReturnsLastElement() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.read()).isEqualTo(1);
  }

  @Test
  void push_WithNullParameter_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.empty().push(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to push null");
  }

  @Test
  void push_WithEmptyStack_ReturnsStackWithOneElement() {
    PersistentStack<Integer> s = PersistentStackImpl.<Integer>empty().push(1);

    assertThat(s).isEqualTo(PersistentStackImpl.singleton(1));
  }

  @Test
  void push_WithSingleElementStack_ReturnsStackWithTwoElements() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2);

    assertThat(s.pop()).isEqualTo(PersistentStackImpl.singleton(1));
  }

  @Test
  void pop_WithNoParameterAndTwoElementStack_ReturnsStackWithOneElement() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2);

    assertThat(s.pop()).isEqualTo(PersistentStackImpl.singleton(1));
  }

  @Test
  void pop_WithParameterGreaterThanStackSize_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).push(2).pop(3))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Attempt to pop empty stack");
  }

  @Test
  void pop_WithParameterLessThanStackSize_ReturnsStackWithElementsRemoved() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2).push(3);

    PersistentStack<Integer> result = s.pop(2);

    assertThat(result).isEqualTo(PersistentStackImpl.singleton(1));
  }

  @Test
  void pop_WithNegativeParameter_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).push(2).pop(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot pop a negative number of elements (-1)");
  }

  @Test
  void pop_WithZeroParameter_ReturnsUnchangedStack() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2);

    PersistentStack<Integer> result = s.pop(0);

    assertThat(result).isEqualTo(s);
  }

  @Test
  void peek_WithStackWithPushedElements_ReturnsLastPushedElement() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2).push(3);

    assertThat(s.peek()).isEqualTo(Optional.of(3));
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithSameElements_ReturnsTrue() {
    PersistentStack<Integer> s1 = PersistentStackImpl.singleton(1).push(2);
    PersistentStack<Integer> s2 = PersistentStackImpl.singleton(1).push(2);

    assertThat(s1).isEqualTo(s2);
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithDifferentLastElement_ReturnsFalse() {
    PersistentStack<Integer> s1 = PersistentStackImpl.singleton(1).push(2).push(3);
    PersistentStack<Integer> s2 = PersistentStackImpl.singleton(1).push(2).push(4);

    assertThat(s1.equals(s2)).isFalse();
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithDifferentNonLastElement_ReturnsFalse() {
    PersistentStack<Integer> s1 = PersistentStackImpl.singleton(1).push(2).push(3);
    PersistentStack<Integer> s2 = PersistentStackImpl.singleton(1).push(4).push(3);

    assertThat(s1.equals(s2)).isFalse();
  }

  @Test
  void isEmpty_WithEmptyStack_ReturnsTrue() {
    PersistentStack<?> s = PersistentStackImpl.empty();

    assertThat(s.isEmpty()).isTrue();
  }

  @Test
  void isEmpty_WithNonEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1);

    assertThat(s.isEmpty()).isFalse();
  }

  @Test
  void isEmpty_WithStackWithAllElementsPopped_ReturnsTrue() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2).pop().pop();

    assertThat(s.isEmpty()).isTrue();
  }

  @Test
  void hashCode_ForInstancesWithSamePropertyValues_ReturnsSameResult() {
    PersistentStack<Integer> s1 = PersistentStackImpl.singleton(1).push(2);
    PersistentStack<Integer> s2 = PersistentStackImpl.singleton(1).push(2);

    assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
  }

  @Test
  void size_WithEmptyStack_ReturnsZero() {
    PersistentStack<?> s = PersistentStackImpl.empty();

    assertThat(s.size()).isEqualTo(0);
  }

  @Test
  void size_WithSingletonStack_ReturnsOne() {
    PersistentStack<?> s = PersistentStackImpl.singleton(0);

    assertThat(s.size()).isEqualTo(1);
  }

  @Test
  void size_WithStackAfterMultipleOperations_ReturnsCorrectSize() {
    PersistentStack<Integer> s = PersistentStackImpl.singleton(1).push(2).pop().push(3).push(4).pop();

    assertThat(s.size()).isEqualTo(2);
  }

  @Test
  void pad_WithTargetSizeGreaterThanStackSize_ReturnsPaddedStack() {
    PersistentStack<Integer> stack = PersistentStackImpl.singleton(1).push(2);

    PersistentStack<Integer> paddedStack = stack.pad(5, 4);

    assertThat(paddedStack).isEqualTo(PersistentStackImpl.singleton(5).push(5).push(1).push(2));
  }

  @Test
  void pad_WithTargetSizeEqualToStackSize_ReturnsUnchangedStack() {
    PersistentStack<Integer> stack = PersistentStackImpl.singleton(1).push(2);

    PersistentStack<Integer> paddedStack = stack.pad(5, 2);

    assertThat(paddedStack).isEqualTo(stack);
  }

  @Test
  void pad_WithTargetSizeLessThanStackSize_ReturnsUnchangedStack() {
    PersistentStack<Integer> stack = PersistentStackImpl.singleton(1).push(2);

    PersistentStack<Integer> paddedStack = stack.pad(5, 1);

    assertThat(paddedStack).isEqualTo(stack);
  }

  @Test
  void pad_WithNegativeSize_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).pad(0, -1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot pad to negative size (-1)");
  }

  @Test
  void pad_WithNullElement_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).pad(null, 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to pad with null");
  }

  @Test
  void truncate_WithTargetSizeLessThanStackSize_ReturnsTruncatedStack() {
    PersistentStack<Integer> stack = PersistentStackImpl.singleton(1).push(2).push(3).push(4);

    PersistentStack<Integer> paddedStack = stack.truncate(2);

    assertThat(paddedStack).isEqualTo(PersistentStackImpl.singleton(3).push(4));
  }

  @Test
  void truncate_WithTargetSizeEqualToStackSize_ReturnsUnchangedStack() {
    PersistentStack<Integer> stack = PersistentStackImpl.singleton(1).push(2);

    PersistentStack<Integer> paddedStack = stack.truncate(2);

    assertThat(paddedStack).isEqualTo(stack);
  }

  @Test
  void truncate_WithTargetSizeGreaterThanStackSize_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).truncate(2))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Attempt to read from empty stack");
  }

  @Test
  void truncate_WithNegativeSize_ThrowsException() {
    assertThatThrownBy(() -> PersistentStackImpl.singleton(1).truncate(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot truncate to negative size (-1)");
  }

  @Test
  void pushAll_WithNonEmptyOther_ReturnsStackWithOtherElementsPrepended() {
    PersistentStackImpl<Integer> target = PersistentStackImpl.singleton(1);
    PersistentStackImpl<Integer> source = PersistentStackImpl.singleton(3).push(2);
    PersistentStackImpl<Integer> expected = PersistentStackImpl.singleton(1).push(2).push(3);

    PersistentStack<Integer> result = target.pushAll(source);

    assertThat(result).isEqualTo(expected);
  }
}