package com.mistraltech.utils;

public final class Preconditions {

  public static void checkArgument(boolean expression, String messageTemplate, Object... messageArgs) {
    if (!expression) {
      throw new IllegalArgumentException(String.format(messageTemplate, messageArgs));
    }
  }

  public static void checkState(boolean expression, String messageTemplate, Object... messageArgs) {
    if (!expression) {
      throw new IllegalStateException(String.format(messageTemplate, messageArgs));
    }
  }
}
