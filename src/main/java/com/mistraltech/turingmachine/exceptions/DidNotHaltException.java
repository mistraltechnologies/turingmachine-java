package com.mistraltech.turingmachine.exceptions;

public class DidNotHaltException extends RuntimeException {

  public DidNotHaltException(int iterations) {
    super(String.format("Machine did not halt within %d steps", iterations));
  }
}
