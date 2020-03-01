package com.mistraltech.turingmachine.model;

/**
 * Represents a machine configuration. This is a combination of the current machine state and the content and current
 * head location of the tape.
 */
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
