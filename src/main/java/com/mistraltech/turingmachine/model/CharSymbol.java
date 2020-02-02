package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CharSymbol implements Symbol {

  public final static CharSymbol BLANK;

  private final static Map<Character, CharSymbol> symbolMap = new HashMap<>();

  static {
    BLANK = CharSymbol.getSymbol('^');
  }

  private final char value;

  private CharSymbol(char value) {
    this.value = value;
  }

  public static Set<CharSymbol> toSymbols(String symbols) {
    return symbols.chars()
        .mapToObj(c -> getSymbol((char) c))
        .collect(Collectors.toSet());
  }

  public static CharSymbol getSymbol(char c) {
    return symbolMap.computeIfAbsent(c, CharSymbol::new);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CharSymbol that = (CharSymbol) o;

    return value == that.value;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }


  @Override
  public int compareTo(Symbol o) {
    checkArgument(o != null, "Cannot compare to null");
    checkArgument(o instanceof CharSymbol, "Cannot compare with type %s", o.getClass().toString());

    return value - ((CharSymbol) o).value;
  }
}
