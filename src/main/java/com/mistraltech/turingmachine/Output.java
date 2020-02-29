package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.model.Configuration;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.utils.CharSymbolUtils;
import java.util.List;

public abstract class Output {

  protected Output() {
  }

  public static Output haltsAndSucceeds(Configuration haltingConfiguration) {
    List<Symbol> outputString = haltingConfiguration.getTape().getOutputString();
    return new HaltsAndSucceeds(outputString);
  }

  public static Output haltsAndFails() {
    return new HaltsAndFails();
  }

  public abstract List<Symbol> getOutputString();

  public abstract boolean succeeded();

  /**
   * An implementation of Output that indicates the machine halted and succeeded.
   */
  private static class HaltsAndSucceeds extends Output {

    private List<Symbol> outputString;

    public HaltsAndSucceeds(List<Symbol> outputString) {
      this.outputString = outputString;
    }

    @Override
    public List<Symbol> getOutputString() {
      return outputString;
    }

    @Override
    public boolean succeeded() {
      return true;
    }

    @Override
    public String toString() {
      return "Output: succeeded [" + CharSymbolUtils.stringFromList(getOutputString()) + "]";
    }
  }

  /**
   * An implementation of Output that indicates the machine halted and failed.
   */
  private static class HaltsAndFails extends Output {

    @Override
    public List<Symbol> getOutputString() {
      throw new IllegalStateException("No output available");
    }

    @Override
    public boolean succeeded() {
      return false;
    }

    @Override
    public String toString() {
      return "Output: failed";
    }
  }
}
