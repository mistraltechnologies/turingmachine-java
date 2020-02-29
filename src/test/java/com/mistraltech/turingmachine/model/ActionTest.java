package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mistraltech.turingmachine.model.factory.IntStateCharSymbolActionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ActionTest {

  private final IntState state1 = IntState.getState(1);
  private final CharSymbol symbolA = CharSymbol.getSymbol('a');
  private final IntState state2 = IntState.getState(2);
  private final CharSymbol symbolB = CharSymbol.getSymbol('b');
  private final Move move = Move.LEFT;
  private final IntStateCharSymbolActionFactory actionFactory = new IntStateCharSymbolActionFactory();

  @Test
  void construct_WithNullState_ThrowsException() {
    Assertions.assertThatThrownBy(() -> new Action(null, symbolA, state2, symbolB, move))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("state cannot be null");
  }

  @Test
  void construct_WithNullSymbol_ThrowsException() {
    Assertions.assertThatThrownBy(() -> new Action(state1, null, state2, symbolB, move))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("symbol cannot be null");
  }

  @Test
  void construct_WithNullNewState_ThrowsException() {
    Assertions.assertThatThrownBy(() -> new Action(state1, symbolA, null, symbolB, move))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("newState cannot be null");
  }

  @Test
  void construct_WithNullNewSymbol_ThrowsException() {
    Assertions.assertThatThrownBy(() -> new Action(state1, symbolA, state2, null, move))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("newSymbol cannot be null");
  }

  @Test
  void construct_WithNullMove_ThrowsException() {
    Assertions.assertThatThrownBy(() -> new Action(state1, symbolA, state2, symbolB, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("move cannot be null");
  }

  @Test
  void construct_WithValidArguments_SetsProperties() {
    Action action = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action.getState()).isEqualTo(state1);
    assertThat(action.getSymbol()).isEqualTo(symbolA);
    assertThat(action.getNewState()).isEqualTo(state2);
    assertThat(action.getNewSymbol()).isEqualTo(symbolB);
    assertThat(action.getMove()).isEqualTo(move);
  }

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_WithSameInstance_ReturnsTrue() {
    Action action = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action.equals(action)).isTrue();
  }

  @Test
  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  void equals_WithDifferentType_ReturnsFalse() {
    Action action = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action.equals("string")).isFalse();
  }

  @Test
  void equals_WithEqualInstance_ReturnsTrue() {
    Action action1 = new Action(state1, symbolA, state2, symbolB, move);
    Action action2 = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action1.equals(action2)).isTrue();
  }

  @Test
  void equals_WithInstanceWithDifferentState_ReturnsFalse() {
    Action action1 = new Action(state1, symbolA, state2, symbolB, move);
    Action action2 = new Action(state2, symbolA, state2, symbolB, move);

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  void equals_WithInstanceWithDifferentSymbol_ReturnsFalse() {
    Action action1 = new Action(state1, symbolA, state2, symbolB, move);
    Action action2 = new Action(state1, symbolB, state2, symbolB, move);

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  void equals_WithInstanceWithDifferentNewState_ReturnsFalse() {
    Action action1 = new Action(state1, symbolA, state1, symbolB, move);
    Action action2 = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  void equals_WithInstanceWithDifferentNewSymbol_ReturnsFalse() {
    Action action1 = new Action(state1, symbolA, state2, symbolA, move);
    Action action2 = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  void equals_WithInstanceWithDifferentMove_ReturnsFalse() {
    Action action1 = new Action(state1, symbolA, state2, symbolB, Move.LEFT);
    Action action2 = new Action(state1, symbolA, state2, symbolB, Move.RIGHT);

    assertThat(action1.equals(action2)).isFalse();
  }

  @Test
  void hashCode_ForInstancesWithSamePropertyValues_ReturnsSameResult() {
    Action action1 = new Action(state1, symbolA, state2, symbolB, move);
    Action action2 = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action1.hashCode()).isEqualTo(action2.hashCode());
  }

  @Test
  void toString_ReturnsExpectedResult() {
    Action action = new Action(state1, symbolA, state2, symbolB, move);

    assertThat(action.toString()).isEqualTo("[1, a, 2, b, L]");
  }

  @Test
  @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
  void compareTo_Null_ThrowsException() {
    Action action = new Action(state1, symbolA, state2, symbolB, move);

    assertThatThrownBy(() -> action.compareTo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cannot compare to null");
  }

  @Test
  void compareTo_HigherValuedInstance_ReturnsNegative() {
    Action action1 = actionFactory.createAction(1, 'a', 1, 'a', 'L');
    Action action2 = actionFactory.createAction(2, 'a', 1, 'a', 'L');
    Action action3 = actionFactory.createAction(2, 'b', 1, 'a', 'L');
    Action action4 = actionFactory.createAction(2, 'b', 2, 'a', 'L');
    Action action5 = actionFactory.createAction(2, 'b', 2, 'b', 'L');
    Action action6 = actionFactory.createAction(2, 'b', 2, 'b', 'R');

    assertThat(action1.compareTo(action2)).isLessThan(0);
    assertThat(action2.compareTo(action3)).isLessThan(0);
    assertThat(action3.compareTo(action4)).isLessThan(0);
    assertThat(action4.compareTo(action5)).isLessThan(0);
    assertThat(action5.compareTo(action6)).isLessThan(0);
  }

  @Test
  void compareTo_LowerValuedInstance_ReturnsPositive() {
    Action action1 = actionFactory.createAction(1, 'a', 1, 'a', 'L');
    Action action2 = actionFactory.createAction(2, 'a', 1, 'a', 'L');
    Action action3 = actionFactory.createAction(2, 'b', 1, 'a', 'L');
    Action action4 = actionFactory.createAction(2, 'b', 2, 'a', 'L');
    Action action5 = actionFactory.createAction(2, 'b', 2, 'b', 'L');
    Action action6 = actionFactory.createAction(2, 'b', 2, 'b', 'R');

    assertThat(action2.compareTo(action1)).isGreaterThan(0);
    assertThat(action3.compareTo(action2)).isGreaterThan(0);
    assertThat(action4.compareTo(action3)).isGreaterThan(0);
    assertThat(action5.compareTo(action4)).isGreaterThan(0);
    assertThat(action6.compareTo(action5)).isGreaterThan(0);
  }

  @Test
  void compareTo_SameValuedInstance_ReturnsZero() {
    Action action1 = actionFactory.createAction(1, 'a', 1, 'a', 'L');
    Action action2 = actionFactory.createAction(1, 'a', 1, 'a', 'L');

    assertThat(action1.compareTo(action2)).isEqualTo(0);
  }
}