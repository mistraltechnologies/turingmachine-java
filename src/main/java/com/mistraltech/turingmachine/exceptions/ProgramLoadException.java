package com.mistraltech.turingmachine.exceptions;

public class ProgramLoadException extends RuntimeException {

  public ProgramLoadException(String message) {
    super(message);
  }

  public ProgramLoadException(String message, Exception cause) {
    super(message, cause);
  }
}
