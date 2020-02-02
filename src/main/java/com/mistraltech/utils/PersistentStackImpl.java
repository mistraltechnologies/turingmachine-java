package com.mistraltech.utils;

import static com.mistraltech.utils.Preconditions.checkArgument;
import static com.mistraltech.utils.Preconditions.checkState;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public final class PersistentStackImpl<T> implements PersistentStack<T> {

  private final T head;
  private final PersistentStack<T> tail;

  public PersistentStackImpl() {
    this.head = null;
    this.tail = null;
  }

  /**
   * Construct a stack with a single initial item.
   *
   * @param head the item on the stack
   */
  public PersistentStackImpl(T head) {
    checkArgument(head != null, "Attempt to construct with null");

    this.head = head;
    this.tail = new PersistentStackImpl<>();
  }

  private PersistentStackImpl(T head, PersistentStack<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public PersistentStack<T> push(T t) {
    checkArgument(t != null, "Attempt to push null");

    return new PersistentStackImpl<>(t, this);
  }

  @Override
  public PersistentStack<T> pop() {
    checkState(head != null, "Attempt to pop empty stack");

    return tail;
  }

  @Override
  public Optional<T> peek() {
    return Optional.ofNullable(head);
  }

  @Override
  public T read() {
    checkState(head != null, "Attempt to read from empty stack");

    return head;
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PersistentStackImpl<?> that = (PersistentStackImpl<?>) o;

    if (!Objects.equals(head, that.head)) {
      return false;
    }
    return Objects.equals(tail, that.tail);
  }

  @Override
  public int hashCode() {
    int result = head != null ? head.hashCode() : 0;
    result = 31 * result + (tail != null ? tail.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PersistentStackImpl.class.getSimpleName() + "[", "]")
        .add("head=" + head)
        .add("tail=" + tail)
        .toString();
  }
}
