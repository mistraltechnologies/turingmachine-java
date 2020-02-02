package com.mistraltech.turingmachine.integration;

import static com.mistraltech.turingmachine.MachineLibrary.CONS0;
import static org.assertj.core.api.Assertions.assertThat;

import com.mistraltech.turingmachine.Output;
import com.mistraltech.turingmachine.TuringMachineSimulatorImpl;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.Tape;
import com.mistraltech.turingmachine.model.TapeImpl;
import com.mistraltech.utils.CharSymbolUtils;
import org.junit.jupiter.api.Test;

class AppIT {

  @Test
  public void compute_WithConsZeroProgramAndEmptyInput_HaltsAndSucceedsWithExpectedOutput() {
    TuringMachineSimulatorImpl tm = new TuringMachineSimulatorImpl(CONS0);

    Tape input = TapeImpl.create(CharSymbol.BLANK);

    Output output = tm.compute(input);

    assertThat(output.succeeded()).isTrue();
    assertThat(CharSymbolUtils.stringFromList(output.getOutputString())).isEqualTo("0");
  }

  @Test
  public void compute_WithConsZeroProgramAndNonEmptyInput_HaltsAndSucceedsWithExpectedOutput() {
    TuringMachineSimulatorImpl tm = new TuringMachineSimulatorImpl(CONS0);

    Tape input = TapeImpl.create(CharSymbol.BLANK, CharSymbolUtils.listFromString("110"));

    Output output = tm.compute(input);

    assertThat(output.succeeded()).isTrue();
    assertThat(CharSymbolUtils.stringFromList(output.getOutputString())).isEqualTo("0110");
  }
}