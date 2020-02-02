package com.mistraltech.utils;

import java.util.Optional;

public interface PersistentStack<T> {

  PersistentStack<T> push(T t);

  PersistentStack<T> pop();

  Optional<T> peek();

  T read();

  boolean isEmpty();
}
