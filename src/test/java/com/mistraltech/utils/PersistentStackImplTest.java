package com.mistraltech.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
class PersistentStackImplTest {

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_ComparedWithSameStack_ReturnsTrue() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.equals(s)).isTrue();
  }

  @Test
  void equals_WithEmptyStackComparedWithNonEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = new PersistentStackImpl<>();

    assertThat(s.equals(new PersistentStackImpl<>(1))).isFalse();
  }

  @Test
  void equals_WithNonEmptyStackComparedWithEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.equals(new PersistentStackImpl<>())).isFalse();
  }

  @Test
  void equals_WithSingleElementStackComparedWithSimilarSingleElementStack_ReturnsTrue() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.equals(new PersistentStackImpl<>(1))).isTrue();
  }

  @Test
  void equals_WithSingleElementStackComparedWithDifferentSingleElementStack_ReturnsFalse() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.equals(new PersistentStackImpl<>(2))).isFalse();
  }

  @Test
  void equals_WithEmptyStackComparedToEmptyStack_ReturnsTrue() {
    PersistentStackImpl<Integer> s = new PersistentStackImpl<>();

    assertThat(s.equals(new PersistentStackImpl<>())).isTrue();
  }

  @Test
  void equals_WithEmptyStackComparedToNull_ReturnsFalse() {
    PersistentStackImpl<Integer> s = new PersistentStackImpl<>();

    assertThat(s.equals(null)).isFalse();
  }

  @Test
  void constructor_WithNullParameter_ThrowsException() {
    assertThatThrownBy(() -> new PersistentStackImpl<>(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to construct with null");
  }

  @Test
  void toString_WithEmptyStack_ReturnsExpectedText() {
    PersistentStack<Integer> s = new PersistentStackImpl<>();

    assertThat(s.toString()).isEqualTo("PersistentStackImpl[head=null, tail=null]");
  }

  @Test
  void toString_WithStackWithOneElement_ReturnsExpectedText() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.toString())
        .isEqualTo("PersistentStackImpl[head=1, tail=PersistentStackImpl[head=null, tail=null]]");
  }

  @Test
  void pop_WithEmptyStack_ThrowsIllegalStateException() {
    PersistentStack<Integer> s = new PersistentStackImpl<>();

    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(s::pop)
        .withMessage("Attempt to pop empty stack");
  }

  @Test
  void pop_WithSingleElementStack_ReturnsEmptyStack() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.pop()).isEqualTo(new PersistentStackImpl<>());
  }

  @Test
  void pop_WithNonEmptyStack_DoesNotModifyOriginalStack() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);
    s.pop();

    assertThat(s).isEqualTo(new PersistentStackImpl<>(1));
  }

  @Test
  void peek_WithEmptyStack_ReturnsEmpty() {
    PersistentStack<Integer> s = new PersistentStackImpl<>();

    assertThat(s.peek()).isEmpty();
  }

  @Test
  void peek_WithNonEmptyStack_ReturnsLastElement() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.peek()).isEqualTo(Optional.of(1));
  }

  @Test
  void read_WithEmptyStack_ThrowsIllegalStateException() {
    PersistentStack<Integer> s = new PersistentStackImpl<>();

    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(s::read)
        .withMessage("Attempt to read from empty stack");
  }

  @Test
  void read_WithNonEmptyStack_ReturnsLastElement() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.read()).isEqualTo(1);
  }

  @Test
  void push_WithNullParameter_ThrowsException() {
    assertThatThrownBy(() -> new PersistentStackImpl<>().push(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Attempt to push null");
  }

  @Test
  void push_WithEmptyStack_ReturnsStackWithOneElement() {
    PersistentStack<Integer> s = new PersistentStackImpl<Integer>().push(1);

    assertThat(s).isEqualTo(new PersistentStackImpl<>(1));
  }

  @Test
  void push_WithSingleElementStack_ReturnsStackWithTwoElements() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1).push(2);

    assertThat(s.pop()).isEqualTo(new PersistentStackImpl<>(1));
  }

  @Test
  void pop_WithTwoElementStack_ReturnsStackWithOneElement() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1).push(2);

    assertThat(s.pop()).isEqualTo(new PersistentStackImpl<>(1));
  }

  @Test
  void peek_WithStackWithPushedElements_ReturnsLastPushedElement() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1).push(2).push(3);

    assertThat(s.peek()).isEqualTo(Optional.of(3));
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithSameElements_ReturnsTrue() {
    PersistentStack<Integer> s1 = new PersistentStackImpl<>(1).push(2);
    PersistentStack<Integer> s2 = new PersistentStackImpl<>(1).push(2);

    assertThat(s1).isEqualTo(s2);
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithDifferentLastElement_ReturnsFalse() {
    PersistentStack<Integer> s1 = new PersistentStackImpl<>(1).push(2).push(3);
    PersistentStack<Integer> s2 = new PersistentStackImpl<>(1).push(2).push(4);

    assertThat(s1.equals(s2)).isFalse();
  }

  @Test
  void equals_WithStackWithPushedElementsComparedWithStackWithDifferentNonLastElement_ReturnsFalse() {
    PersistentStack<Integer> s1 = new PersistentStackImpl<>(1).push(2).push(3);
    PersistentStack<Integer> s2 = new PersistentStackImpl<>(1).push(4).push(3);

    assertThat(s1.equals(s2)).isFalse();
  }

  @Test
  void isEmpty_WithEmptyStack_ReturnsTrue() {
    PersistentStack<?> s = new PersistentStackImpl<>();

    assertThat(s.isEmpty()).isTrue();
  }

  @Test
  void isEmpty_WithNonEmptyStack_ReturnsFalse() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1);

    assertThat(s.isEmpty()).isFalse();
  }

  @Test
  void isEmpty_WithStackWithAllElementsPopped_ReturnsTrue() {
    PersistentStack<Integer> s = new PersistentStackImpl<>(1).push(2).pop().pop();

    assertThat(s.isEmpty()).isTrue();
  }

  @Test
  void hashCode_ForInstancesWithSamePropertyValues_ReturnsSameResult() {
    PersistentStack<Integer> s1 = new PersistentStackImpl<>(1).push(2);
    PersistentStack<Integer> s2 = new PersistentStackImpl<>(1).push(2);

    assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
  }
}