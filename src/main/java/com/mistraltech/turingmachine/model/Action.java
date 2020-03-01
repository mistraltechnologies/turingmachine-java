package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.StringJoiner;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * A 5-tuple describing an action condition (the current state and symbol at the current tape location) and the new
 * machine state (new state, symbol to write at the current tape location and tape head movement).
 */
public final class Action implements Comparable<Action> {

  private final State state;
  private final Symbol symbol;
  private final State newState;
  private final Symbol newSymbol;
  private final Move move;

  public Action(State state, Symbol symbol, State newState, Symbol newSymbol, Move move) {
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

  public State getState() {
    return state;
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public State getNewState() {
    return newState;
  }

  public Symbol getNewSymbol() {
    return newSymbol;
  }

  public Move getMove() {
    return move;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Action action = (Action) other;

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
  public int compareTo(Action other) {
    checkArgument(other != null, "Cannot compare to null");

    return new CompareToBuilder()
        .append(this.state, other.getState())
        .append(this.symbol, other.getSymbol())
        .append(this.newState, other.getNewState())
        .append(this.newSymbol, other.getNewSymbol())
        .append(this.move, other.getMove())
        .toComparison();
  }
}
