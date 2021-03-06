package com.mistraltech.turingmachine.model.factory;

import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.IntState;
import com.mistraltech.turingmachine.model.Move;
import com.mistraltech.turingmachine.model.State;
import com.mistraltech.turingmachine.model.Symbol;

/**
 * Factory to construct actions based on state type IntState and symbol type CharSymbol.
 */
public class IntStateCharSymbolActionFactory {

  public IntStateCharSymbolActionFactory() {
  }

  public Action createAction(int stateCode, char symbolCode, int newStateCode, char newSymbolCode, char moveCode) {
    State state = IntState.getState(stateCode);
    Symbol symbol = CharSymbol.getSymbol(symbolCode);
    State newState = IntState.getState(newStateCode);
    Symbol newSymbol = CharSymbol.getSymbol(newSymbolCode);

    Move move = Move.findByCode(moveCode)
        .orElseThrow(() -> new IllegalArgumentException(String.format("moveCode '%c' not recognised", moveCode)));

    return new Action(state, symbol, newState, newSymbol, move);
  }
}
