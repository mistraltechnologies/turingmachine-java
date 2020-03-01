package com.mistraltech.utils;

import static com.mistraltech.utils.Preconditions.checkArgument;
import static com.mistraltech.utils.Preconditions.checkState;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public final class PersistentStackImpl<T> implements PersistentStack<T> {

  private final T head;
  private final PersistentStackImpl<T> tail;
  private final int size;

  private PersistentStackImpl() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  private PersistentStackImpl(T head) {
    checkArgument(head != null, "Attempt to construct with null item");

    this.head = head;
    this.tail = new PersistentStackImpl<>();
    this.size = 1;
  }

  private PersistentStackImpl(T head, PersistentStackImpl<T> tail) {
    this.head = head;
    this.tail = tail;
    this.size = 1 + tail.size();
  }

  /**
   * Return a new empty persistent stack.
   */
  public static <T> PersistentStackImpl<T> empty() {
    return new PersistentStackImpl<>();
  }

  /**
   * Return a new persistent stack with a single initial item.
   *
   * @param head the item on the stack
   */
  public static <T> PersistentStackImpl<T> singleton(T head) {
    return new PersistentStackImpl<>(head);
  }

  /**
   * Return a new persistent stack initialised with a list of elements.
   * The stack is initialised such that the elements will be popped in the order given in initial.
   *
   * @param initial the list of elements the stack will contain
   */
  public static <T> PersistentStackImpl<T> from(List<T> initial) {
    checkArgument(initial != null, "Attempt to construct with null list");

    PersistentStackImpl<T> stack = empty();

    for(int i = initial.size() - 1; i >= 0; i--) {
      stack = stack.push(initial.get(i));
    }

    return stack;
  }

  @Override
  public PersistentStackImpl<T> push(T t) {
    checkArgument(t != null, "Attempt to push null");

    return new PersistentStackImpl<>(t, this);
  }

  @Override
  public PersistentStackImpl<T> pop() {
    checkState(head != null, "Attempt to pop empty stack");

    return tail;
  }

  @Override
  public PersistentStackImpl<T> pop(int n) {
    checkArgument(n >= 0, "Cannot pop a negative number of elements (%d)", n);

    PersistentStackImpl<T> result = this;

    while (n-- > 0) {
      result = result.pop();
    }

    return result;
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
  public int size() {
    return size;
  }

  @Override
  public PersistentStackImpl<T> pad(T t, int to) {
    checkArgument(t != null, "Attempt to pad with null");
    checkArgument(to >= 0, "Cannot pad to negative size (%d)", to);

    if (to > size) {
      PersistentStackImpl<T> newStack = empty();
      for (int i = to - size(); i > 0; i--) {
        newStack = newStack.push(t);
      }
      return copyTo(newStack);
    } else {
      return this;
    }
  }

  private PersistentStackImpl<T> copyTo(PersistentStackImpl<T> other) {
    if (isEmpty()) {
      return other;
    }

    T t = read();
    return pop().copyTo(other).push(t);
  }

  @Override
  public PersistentStackImpl<T> truncate(int to) {
    checkArgument(to >= 0, "Cannot truncate to negative size (%d)", to);
    return uncheckedTruncate(to);
  }

  private PersistentStackImpl<T> uncheckedTruncate(int to) {
    if (to == 0) {
      return empty();
    } else {
      T t = this.read();
      return this.pop().uncheckedTruncate(to - 1).push(t);
    }
  }

  @Override
  public PersistentStack<T> pushAll(PersistentStack<T> other) {
    PersistentStack<T> result = this;

    while (!other.isEmpty()) {
      result = result.push(other.read());
      other = other.pop();
    }

    return result;
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
