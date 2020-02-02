package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
class TuringMachineTest {

  public static final IntState STATE_1 = IntState.getState(1);
  public static final IntState STATE_2 = IntState.getState(2);
  public static final CharSymbol SYMBOL_BLANK = CharSymbol.BLANK;
  public static final CharSymbol SYMBOL_1 = CharSymbol.getSymbol('1');
  public static final CharSymbol SYMBOL_0 = CharSymbol.getSymbol('0');
  public static final CharSymbol SYMBOL_A = CharSymbol.getSymbol('a');

  private final Set<State> states = Set.of(STATE_1, STATE_2);
  private final Set<State> finalStates = Set.of(STATE_2);
  private final State initialState = STATE_1;
  private final Set<State> emptyStates = Set.of();
  private final Set<CharSymbol> symbols = Set.of(SYMBOL_BLANK, SYMBOL_1, SYMBOL_0, SYMBOL_A);
  private final Set<CharSymbol> inputSymbols = Set.of(SYMBOL_1, SYMBOL_0);
  private final Action action1 = new ActionImpl(STATE_1, SYMBOL_0, STATE_2, SYMBOL_1, Move.RIGHT);
  private final Program program = new ProgramImpl(Set.of(action1));

  @Test
  void constructor_WithNullStateSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        null, finalStates, initialState, symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("states cannot be null");
  }

  @Test
  void constructor_WithEmptyStateSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        emptyStates, finalStates, initialState, symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("states cannot be empty");
  }

  @Test
  void constructor_WithNullFinalStateSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, null, initialState, symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("finalStates cannot be null");
  }

  @Test
  void constructor_WithFinalStateSetNotASubsetOfStateSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, Set.of(IntState.getState(9)), initialState, symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("finalStates elements must exist in states");
  }

  @Test
  void constructor_WithNullInitialState_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, null, symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("initialState cannot be null");
  }

  @Test
  void constructor_WithInitialStateNotInStateSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, IntState.getState(9), symbols, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("initialState must exist in states");
  }

  @Test
  void constructor_WithNullSymbolSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, null, inputSymbols, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("symbols cannot be null");
  }

  @Test
  void constructor_WithNullInputSymbolSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, null, SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("inputSymbols cannot be null");
  }

  @Test
  void constructor_WithInputSymbolSetNotASubsetOfSymbolSet_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, CharSymbol.toSymbols("x"), SYMBOL_BLANK, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("inputSymbols elements must exist in symbols");
  }

  @Test
  void constructor_WithNullBlankSymbol_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, inputSymbols, null, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("blankSymbol cannot be null");
  }

  @Test
  void constructor_WithBlankSymbolNotInSymbols_ThrowsException() {
    CharSymbol blank = CharSymbol.getSymbol('x');

    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, inputSymbols, blank, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("blankSymbol must exist in symbols");
  }

  @Test
  void constructor_WithBlankSymbolInInputSymbols_ThrowsException() {
    CharSymbol[] inputSymbolArray = inputSymbols.toArray(new CharSymbol[0]);
    CharSymbol blank = inputSymbolArray[inputSymbolArray.length - 1];

    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, inputSymbols, blank, program))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("blankSymbol must not exist in inputSymbols");
  }

  @Test
  void constructor_WithNullProgram_ThrowsException() {
    assertThatThrownBy(() -> new TuringMachine(
        states, finalStates, initialState, symbols, inputSymbols, SYMBOL_BLANK, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("program cannot be null");
  }

  @Test
  void constructor_WithValidArguments_DoesNotThrowException() {
    assertThatCode(() -> new TuringMachine(
        states, finalStates, initialState, symbols, inputSymbols, SYMBOL_BLANK, program))
        .doesNotThrowAnyException();
  }

  @Test
  void getBlank_ReturnsBlankSymbolAssignedAtConstruction() {
    CharSymbol blank = CharSymbol.getSymbol('a');

    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        blank, program);

    assertThat(tm.getBlank()).isEqualTo(blank);
  }

  @Test
  void getInitialState_ReturnsInitialStateAssignedAtConstruction() {
    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, program);

    assertThat(tm.getInitialState()).isEqualTo(initialState);
  }

  @Test
  void isFinalState_WithStateInFinalStates_ReturnsTrue() {
    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, program);

    assertThat(tm.isFinalState(IntState.getState(2))).isTrue();
  }

  @Test
  void isFinalState_WithStateNotInFinalStates_ReturnsFalse() {
    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, program);

    assertThat(tm.isFinalState(IntState.getState(1))).isFalse();
  }

  @Test
  void findAction_WithValidPair_ReturnsAction() {
    Action action1 = new ActionImpl(STATE_1, SYMBOL_0, STATE_1, SYMBOL_0, Move.NONE);
    Action action2 = new ActionImpl(STATE_1, SYMBOL_1, STATE_1, SYMBOL_0, Move.NONE);
    Action action3 = new ActionImpl(STATE_2, SYMBOL_1, STATE_1, SYMBOL_0, Move.NONE);

    Program p = new ProgramImpl(Set.of(action1, action2, action3));

    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, p);

    assertThat(tm.findAction(STATE_1, SYMBOL_0)).isPresent().contains(action1);
    assertThat(tm.findAction(STATE_1, SYMBOL_1)).isPresent().contains(action2);
    assertThat(tm.findAction(STATE_2, SYMBOL_1)).isPresent().contains(action3);
  }

  @Test
  void findAction_WithUnknownPair_ReturnsEmpty() {
    Action action1 = new ActionImpl(STATE_1, SYMBOL_0, STATE_1, SYMBOL_0, Move.NONE);
    Action action3 = new ActionImpl(STATE_2, SYMBOL_1, STATE_1, SYMBOL_0, Move.NONE);

    Program p = new ProgramImpl(Set.of(action1, action3));

    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, p);

    assertThat(tm.findAction(STATE_1, SYMBOL_1)).isNotPresent();
  }

  @Test
  void toString_ReturnsExpectedResult() {
    TuringMachine tm = new TuringMachine(states, finalStates, initialState, symbols, inputSymbols,
        SYMBOL_BLANK, program);

    assertThat(tm.toString())
        .isEqualTo("TuringMachine[initialState=1, finalStates=[2], blankSymbol=^, program={ [1, 0, 2, 1, R] }]");
  }
}