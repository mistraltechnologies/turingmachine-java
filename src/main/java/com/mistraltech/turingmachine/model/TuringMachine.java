package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

public class TuringMachine {

  private final Set<? extends State> finalStates;
  private final Symbol blankSymbol;
  private final State initialState;
  private final Program program;

  public TuringMachine(Set<? extends State> states, Set<? extends State> finalStates, State initialState,
      Set<? extends Symbol> symbols, Set<? extends Symbol> inputSymbols, Symbol blankSymbol, Program program) {
    checkArgument(states != null, "states cannot be null");
    checkArgument(!states.isEmpty(), "states cannot be empty");
    checkArgument(finalStates != null, "finalStates cannot be null");
    checkArgument(states.containsAll(finalStates), "finalStates elements must exist in states");
    checkArgument(initialState != null, "initialState cannot be null");
    checkArgument(states.contains(initialState), "initialState must exist in states");
    checkArgument(symbols != null, "symbols cannot be null");
    checkArgument(inputSymbols != null, "inputSymbols cannot be null");
    checkArgument(symbols.containsAll(inputSymbols), "inputSymbols elements must exist in symbols");
    checkArgument(blankSymbol != null, "blankSymbol cannot be null");
    checkArgument(symbols.contains(blankSymbol), "blankSymbol must exist in symbols");
    checkArgument(!inputSymbols.contains(blankSymbol), "blankSymbol must not exist in inputSymbols");
    checkArgument(program != null, "program cannot be null");

    this.finalStates = finalStates;
    this.initialState = initialState;
    this.blankSymbol = blankSymbol;
    this.program = program;
  }

  public State getInitialState() {
    return initialState;
  }

  public boolean isFinalState(State state) {
    return finalStates.contains(state);
  }

  public Optional<Action> findAction(State state, Symbol symbol) {
    return program.apply(state, symbol);
  }

  public Symbol getBlank() {
    return blankSymbol;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TuringMachine.class.getSimpleName() + "[", "]")
        .add("initialState=" + initialState)
        .add("finalStates=" + finalStates)
        .add("blankSymbol=" + blankSymbol)
        .add("program=" + program)
        .toString();
  }
}
