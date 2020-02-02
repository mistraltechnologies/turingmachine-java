package com.mistraltech.utils;

import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.Symbol;
import java.util.List;
import java.util.stream.Collectors;

public class CharSymbolUtils {

  public static String stringFromList(List<Symbol> symbols) {
    return symbols.stream()
        .map(Object::toString)
        .collect(Collectors.joining(""));
  }

  public static List<Symbol> listFromString(String symbols) {
    return symbols
        .chars()
        .mapToObj(c -> CharSymbol.getSymbol((char) c))
        .collect(Collectors.toList());
  }
}
