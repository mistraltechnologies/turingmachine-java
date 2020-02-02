package com.mistraltech.utils;

import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class PStackUtils<T> {

  private T pad;

  public PStackUtils(T pad) {
    this.pad = pad;
  }

  public PStack<T> pad(PStack<T> list, int paddedLength) {
    Preconditions.checkArgument(list != null, "list cannot be null");

    int paddingLength = paddedLength - list.size();
    if (paddingLength < 1) {
      return list;
    }

    PStack<T> padding = getPadding(paddingLength);
    return padding.plusAll(ConsPStack.<T>empty().plusAll(list));
  }

  private PStack<T> getPadding(int length) {
    PStack<T> padding = ConsPStack.empty();

    while (length > 0) {
      padding = padding.plus(pad);
      length--;
    }

    return padding;
  }
}
