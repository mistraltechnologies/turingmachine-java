package com.mistraltech.turingmachine.model;

import java.util.Arrays;
import java.util.Optional;

public enum Move {
  LEFT('L'),
  RIGHT('R'),
  NONE('0');

  private final char code;

  Move(char code) {
    this.code = code;
  }

  public static Optional<Move> findByCode(char code) {
    return Arrays.stream(values()).filter(m -> m.code == code).findAny();
  }

  public char getCode() {
    return code;
  }

  @Override
  public String toString() {
    return String.valueOf(code);
  }
}
