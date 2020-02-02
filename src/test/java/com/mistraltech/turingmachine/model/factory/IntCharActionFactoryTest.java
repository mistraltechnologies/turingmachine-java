package com.mistraltech.turingmachine.model.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.IntState;
import com.mistraltech.turingmachine.model.Move;
import org.junit.jupiter.api.Test;

class IntCharActionFactoryTest {

  private static final IntState STATE_1 = IntState.getState(1);
  private static final IntState STATE_2 = IntState.getState(2);
  private static final CharSymbol SYMBOL_A = CharSymbol.getSymbol('a');
  private static final CharSymbol CHAR_SYMBOL_B = CharSymbol.getSymbol('b');

  @Test
  void createAction_WithValidArguments_CreatesCorrectlyPopulatedAction() {
    IntStateCharSymbolActionFactory factory = new IntStateCharSymbolActionFactory();

    Action action = factory.createAction(1, 'a', 2, 'b', 'R');

    assertThat(action.getState()).isEqualTo(STATE_1);
    assertThat(action.getSymbol()).isEqualTo(SYMBOL_A);
    assertThat(action.getNewState()).isEqualTo(STATE_2);
    assertThat(action.getNewSymbol()).isEqualTo(CHAR_SYMBOL_B);
    assertThat(action.getMove()).isEqualTo(Move.RIGHT);
  }

  @Test
  void create_WithInvalidMoveCode_ThrowsException() {
    IntStateCharSymbolActionFactory factory = new IntStateCharSymbolActionFactory();

    assertThatThrownBy(() -> factory.createAction(1, 'a', 2, 'b', 'x'))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("moveCode 'x' not recognised");
  }
}