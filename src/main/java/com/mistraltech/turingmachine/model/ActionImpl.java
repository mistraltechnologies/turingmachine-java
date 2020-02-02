package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.StringJoiner;
import org.apache.commons.lang3.builder.CompareToBuilder;

public final class ActionImpl implements Action {

  private final State state;
  private final Symbol symbol;
  private final State newState;
  private final Symbol newSymbol;
  private final Move move;

  public ActionImpl(State state, Symbol symbol, State newState, Symbol newSymbol, Move move) {
    checkArgument(state != null, "state cannot be null");
    checkArgument(symbol != null, "symbol cannot be null");
    checkArgument(newState != null, "newState cannot be null");
    checkArgument(newSymbol != null, "newSymbol cannot be null");
    checkArgument(move != null, "move cannot be null");

    this.state = state;
    this.symbol = symbol;
    this.newState = newState;
    this.newSymbol = newSymbol;
    this.move = move;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public Symbol getSymbol() {
    return symbol;
  }

  @Override
  public State getNewState() {
    return newState;
  }

  @Override
  public Symbol getNewSymbol() {
    return newSymbol;
  }

  @Override
  public Move getMove() {
    return move;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ActionImpl action = (ActionImpl) o;

    if (!state.equals(action.state)) {
      return false;
    }
    if (!symbol.equals(action.symbol)) {
      return false;
    }
    if (!newState.equals(action.newState)) {
      return false;
    }
    if (!newSymbol.equals(action.newSymbol)) {
      return false;
    }
    return move == action.move;
  }

  @Override
  public int hashCode() {
    int result = state.hashCode();
    result = 31 * result + symbol.hashCode();
    result = 31 * result + newState.hashCode();
    result = 31 * result + newSymbol.hashCode();
    result = 31 * result + move.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "[", "]")
        .add(state.toString())
        .add(symbol.toString())
        .add(newState.toString())
        .add(newSymbol.toString())
        .add(move.toString())
        .toString();
  }

  @Override
  public int compareTo(Action o) {
    checkArgument(o != null, "Cannot compare to null");

    return new CompareToBuilder()
        .append(this.state, o.getState())
        .append(this.symbol, o.getSymbol())
        .append(this.newState, o.getNewState())
        .append(this.newSymbol, o.getNewSymbol())
        .append(this.move, o.getMove())
        .toComparison();
  }
}
