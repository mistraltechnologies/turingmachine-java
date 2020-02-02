package com.mistraltech.turingmachine.model;

public class Configuration {

  private final State state;
  private final Tape tape;

  public Configuration(State state, Tape tape) {
    this.state = state;
    this.tape = tape;
  }

  public State getState() {
    return state;
  }

  public Tape getTape() {
    return tape;
  }
}
