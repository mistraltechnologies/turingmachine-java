package com.mistraltech.utils;

import java.util.Optional;

public interface PersistentStack<T> {

  PersistentStack<T> push(T t);

  PersistentStack<T> pop();

  PersistentStack<T> pop(int n);

  Optional<T> peek();

  T read();

  boolean isEmpty();

  int size();

  PersistentStack<T> pad(T t, int to);

  PersistentStack<T> truncate(int to);

  PersistentStack<T> pushAll(PersistentStack<? extends T> other);
}
