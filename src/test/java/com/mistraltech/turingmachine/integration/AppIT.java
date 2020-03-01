package com.mistraltech.turingmachine.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.mistraltech.turingmachine.MachineLibrary;
import com.mistraltech.turingmachine.MachineLibrary.MachineType;
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
    TuringMachineSimulatorImpl tm = getSimulator(MachineType.CONS0);

    Tape input = TapeImpl.create(CharSymbol.BLANK);

    Output output = tm.compute(input);

    assertThat(output.succeeded()).isTrue();
    assertThat(CharSymbolUtils.symbolListToString(output.getOutputString())).isEqualTo("0");
  }

  @Test
  public void compute_WithConsZeroProgramAndNonEmptyInput_HaltsAndSucceedsWithExpectedOutput() {
    TuringMachineSimulatorImpl tm = getSimulator(MachineType.CONS0);

    Tape input = TapeImpl.create(CharSymbol.BLANK, CharSymbolUtils.stringToSymbolList("110"));

    Output output = tm.compute(input);

    assertThat(output.succeeded()).isTrue();
    assertThat(CharSymbolUtils.symbolListToString(output.getOutputString())).isEqualTo("0110");
  }

  private TuringMachineSimulatorImpl getSimulator(MachineType machineType) {
    return new TuringMachineSimulatorImpl(MachineLibrary.getMachine(machineType));
  }
}