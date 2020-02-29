package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of Symbol using a single character. The blank symbol is represented by the carat character ('^').
 * CharSymbols cannot be constructed directly. Use one of the static factory methods to construct and retrieve
 * CharSymbol instances.
 */
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
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    CharSymbol that = (CharSymbol) other;

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
  public int compareTo(Symbol other) {
    checkArgument(other != null, "Cannot compare to null");
    checkArgument(other instanceof CharSymbol, "Cannot compare with type %s", other.getClass().toString());

    return value - ((CharSymbol) other).value;
  }
}
