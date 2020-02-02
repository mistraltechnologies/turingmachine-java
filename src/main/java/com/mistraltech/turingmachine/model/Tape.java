package com.mistraltech.turingmachine.model;

import java.util.List;

public interface Tape {

  Tape apply(Symbol newSymbol, Move move);

  List<Symbol> getOutputString();

  List<Symbol> getString(int from, int to);

  Symbol getCurrentSymbol();
}
