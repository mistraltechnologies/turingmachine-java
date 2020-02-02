package com.mistraltech.turingmachine.model.builder;

import com.mistraltech.turingmachine.model.Program;
import com.mistraltech.turingmachine.model.State;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.utils.Preconditions;
import java.util.Collection;
import java.util.Set;

public final class TuringMachineBuilder {

  private Set<State> states;
  private Set<State> finalStates;
  private State initialState;
  private Set<Symbol> symbols;
  private Set<Symbol> inputSymbols;
  private Symbol blankSymbol;
  private Program program;

  private TuringMachineBuilder() {
  }

  public static TuringMachineBuilder aTuringMachine() {
    return new TuringMachineBuilder();
  }

  public TuringMachineBuilder withStates(Collection<? extends State> states) {
    Preconditions.checkArgument(states != null, "states cannot be null");

    this.states = Set.copyOf(states);
    return this;
  }

  public TuringMachineBuilder withFinalStates(Collection<? extends State> finalStates) {
    Preconditions.checkArgument(finalStates != null, "finalStates cannot be null");

    this.finalStates = Set.copyOf(finalStates);
    return this;
  }

  public TuringMachineBuilder withInitialState(State initialState) {
    Preconditions.checkArgument(initialState != null, "initialState cannot be null");

    this.initialState = initialState;
    return this;
  }

  public TuringMachineBuilder withSymbols(Collection<? extends Symbol> symbols) {
    Preconditions.checkArgument(finalStates != null, "symbols cannot be null");

    this.symbols = Set.copyOf(symbols);
    return this;
  }

  public TuringMachineBuilder withInputSymbols(Collection<? extends Symbol> inputSymbols) {
    Preconditions.checkArgument(finalStates != null, "inputSymbols cannot be null");

    this.inputSymbols = Set.copyOf(inputSymbols);
    return this;
  }

  public TuringMachineBuilder withBlankSymbol(Symbol blankSymbol) {
    this.blankSymbol = blankSymbol;
    return this;
  }

  public TuringMachineBuilder withProgram(Program program) {
    this.program = program;
    return this;
  }

  public TuringMachine build() {
    return new TuringMachine(states, finalStates, initialState, symbols, inputSymbols, blankSymbol, program);
  }
}
