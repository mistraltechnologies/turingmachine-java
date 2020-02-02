package com.mistraltech.turingmachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mistraltech.turingmachine.exceptions.DidNotHaltException;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.IntState;
import com.mistraltech.turingmachine.model.Program;
import com.mistraltech.turingmachine.model.Tape;
import com.mistraltech.turingmachine.model.TapeImpl;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.turingmachine.model.builder.ProgramBuilder;
import com.mistraltech.turingmachine.model.builder.TuringMachineBuilder;
import com.mistraltech.turingmachine.model.factory.IntStateCharSymbolActionFactory;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TuringMachineSimulatorImplTest {

  private static final IntState STATE_0 = IntState.getState(0);
  private static final IntState STATE_1 = IntState.getState(1);
  private static final IntState STATE_2 = IntState.getState(2);
  private static final IntState STATE_3 = IntState.getState(3);
  private static final IntState STATE_4 = IntState.getState(4);

  private static final CharSymbol SYMBOL_1 = CharSymbol.getSymbol('1');

  private static final Tape BLANK_TAPE = TapeImpl.create(CharSymbol.BLANK);

  private static IntStateCharSymbolActionFactory actionFactory = new IntStateCharSymbolActionFactory();

  private static final Program PROGRAM_UNDEF = ProgramBuilder.aProgram()
      .withActions(Set.of())
      .build();

  private static final Program LOOPER = ProgramBuilder.aProgram()
      .withActions(Set.of(actionFactory.createAction(0, '^', 0, '^', '0')))
      .build();

  private static final Program FOUR_ONES = ProgramBuilder.aProgram()
      .withActions(Set.of(
          actionFactory.createAction(0, '^', 1, '1', 'R'),
          actionFactory.createAction(1, '^', 2, '1', 'R'),
          actionFactory.createAction(2, '^', 3, '1', 'R'),
          actionFactory.createAction(3, '^', 4, '1', '0')))
      .build();


  @Test
  public void compute_WithNullInput_ThrowsException() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0))
        .withFinalStates(Set.of())
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(PROGRAM_UNDEF)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    assertThatThrownBy(() -> turingMachineSimulator.compute(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("input must not be null");
  }

  @Test
  public void compute_WithProgramWithOneNonFinalStateWithoutMatchingAction_HaltsAndFails() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0))
        .withFinalStates(Set.of())
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(PROGRAM_UNDEF)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    Output output = turingMachineSimulator.compute(BLANK_TAPE);

    assertThat(output.succeeded()).isFalse();
  }

  @Test
  public void compute_WithProgramWithOneFinalState_HaltsAndSucceedsWithEmptyOutput() {
    Program trivial = ProgramBuilder.aProgram()
        .withActions(Set.of())
        .build();

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0))
        .withFinalStates(Set.of(STATE_0))
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(trivial)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    Output output = turingMachineSimulator.compute(BLANK_TAPE);

    assertThat(output.succeeded()).isTrue();
    assertThat(output.getOutputString()).isEmpty();
  }

  @Test
  public void compute_WithProgramWithLoopingState_ThrowsDidNotHaltException() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0))
        .withFinalStates(Set.of())
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(LOOPER)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    assertThatThrownBy(() -> turingMachineSimulator.compute(BLANK_TAPE))
        .isInstanceOf(DidNotHaltException.class);
  }

  @Test
  public void compute_WithProgramWithThatHaltsInSeveralMoves_SucceedsAndHalts() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0, STATE_1, STATE_2, STATE_3, STATE_4))
        .withFinalStates(Set.of(STATE_4))
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(FOUR_ONES)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    Output output = turingMachineSimulator.compute(BLANK_TAPE);

    assertThat(output.succeeded()).isTrue();
    assertThat(output.getOutputString()).isEqualTo(
        List.of(SYMBOL_1, SYMBOL_1, SYMBOL_1, SYMBOL_1));
  }

  @Test
  public void compute_WithProgramWithThatHaltsInSeveralMovesAndLowerMaxIterations_ThrowsDidNotHaltException() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0, STATE_1, STATE_2, STATE_3, STATE_4))
        .withFinalStates(Set.of(STATE_4))
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(FOUR_ONES)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    assertThatThrownBy(() -> turingMachineSimulator.compute(BLANK_TAPE, 4))
        .isInstanceOf(DidNotHaltException.class);
  }

  @Test
  public void compute_WithProgramWithThatHaltsInSeveralMovesAndEqualToMaxIterations_HaltsAndSucceeds() {

    TuringMachine tm = TuringMachineBuilder.aTuringMachine()
        .withStates(Set.of(STATE_0, STATE_1, STATE_2, STATE_3, STATE_4))
        .withFinalStates(Set.of(STATE_4))
        .withInitialState(STATE_0)
        .withSymbols(Set.of(CharSymbol.BLANK))
        .withBlankSymbol(CharSymbol.BLANK)
        .withInputSymbols(Set.of())
        .withProgram(FOUR_ONES)
        .build();

    TuringMachineSimulatorImpl turingMachineSimulator = new TuringMachineSimulatorImpl(tm);

    Output output = turingMachineSimulator.compute(BLANK_TAPE);

    assertThat(output.succeeded()).isTrue();
  }
}