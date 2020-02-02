package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mistraltech.turingmachine.model.factory.IntStateCharSymbolActionFactory;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProgramImplTest {

  private final static IntStateCharSymbolActionFactory ACTION_FACTORY = new IntStateCharSymbolActionFactory();
  private final static Action ACTION_1 = ACTION_FACTORY.createAction(1, '0', 2, '1', 'R');
  private final static Action ACTION_2 = ACTION_FACTORY.createAction(2, '1', 1, '0', 'L');

  @Test
  @SuppressWarnings("ConstantConditions")
  void construct_WithNullActions_ThrowsException() {
    assertThatThrownBy(() -> new ProgramImpl(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("actions must not be null");
  }

  @Test
  void construct_WithNonUniqueStateAndSymbol_ThrowsException() {
    Action action1a = ACTION_FACTORY.createAction(1, '0', 1, '0', 'L');
    Set<Action> actions = Set.of(ACTION_1, action1a);

    assertThatThrownBy(() -> new ProgramImpl(actions))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("state and symbol combination must be unique - multiple actions with state '1' and symbol '0'");
  }

  @Test
  void construct_WithValidActions_DoesNotThrowException() {
    assertThatCode(() -> new ProgramImpl(Set.of(ACTION_1, ACTION_2)))
        .doesNotThrowAnyException();
  }

  @Test
  void apply_WithKnownStateAndSymbol_ReturnsMatchingAction() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.apply(IntState.getState(1), CharSymbol.getSymbol('0'))).contains(ACTION_1);
    assertThat(program.apply(IntState.getState(2), CharSymbol.getSymbol('1'))).contains(ACTION_2);
  }

  @Test
  void apply_WithKnownStateAndUnknownSymbol_ReturnsEmpty() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.apply(IntState.getState(1), CharSymbol.getSymbol('x'))).isEmpty();
  }

  @Test
  void apply_WithUnknownStateAndKnownSymbol_ReturnsEmpty() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.apply(IntState.getState(9), CharSymbol.getSymbol('0'))).isEmpty();
  }

  @Test
  @SuppressWarnings("EqualsWithItself")
  void equals_WithSameInstance_ReturnsTrue() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.equals(program)).isTrue();
  }

  @Test
  void equals_WithAProgramWithTheSameActions_ReturnsTrue() {
    ProgramImpl program1 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));
    Program program2 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program1.equals(program2)).isTrue();
  }

  @Test
  void equals_WithAnInstanceWithADifferentInstanceOfSameAction_ReturnsTrue() {
    Action action2Copy = new ActionImpl(ACTION_2.getState(), ACTION_2.getSymbol(),
        ACTION_2.getNewState(), ACTION_2.getNewSymbol(), ACTION_2.getMove());

    ProgramImpl program1 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));
    Program program2 = new ProgramImpl(Set.of(ACTION_1, action2Copy));

    assertThat(program1.equals(program2)).isTrue();
  }

  @Test
  @SuppressWarnings("ConstantConditions")
  void equals_WithNull_ReturnsFalse() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.equals(null)).isFalse();
  }

  @Test
  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  void equals_WithADifferentType_ReturnsFalse() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.equals("string")).isFalse();
  }

  @Test
  void equals_WithAnInstanceWithADifferentAction_ReturnsFalse() {
    Action action3 = new ActionImpl(ACTION_2.getState(), ACTION_2.getSymbol(),
        ACTION_2.getNewState(), ACTION_2.getNewSymbol(), Move.NONE);

    ProgramImpl program1 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));
    Program program2 = new ProgramImpl(Set.of(ACTION_1, action3));

    assertThat(program1.equals(program2)).isFalse();
  }

  @Test
  void equals_WithAnInstanceWithDifferentActionCount_ReturnsFalse() {
    ProgramImpl program1 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));
    Program program2 = new ProgramImpl(Set.of(ACTION_1));

    assertThat(program1.equals(program2)).isFalse();
  }

  @Test
  void hashCode_ForEquivalentInstances_ReturnsSameResult() {
    Action action2Copy = new ActionImpl(ACTION_2.getState(), ACTION_2.getSymbol(),
        ACTION_2.getNewState(), ACTION_2.getNewSymbol(), ACTION_2.getMove());

    ProgramImpl program1 = new ProgramImpl(Set.of(ACTION_1, ACTION_2));
    ProgramImpl program2 = new ProgramImpl(Set.of(ACTION_1, action2Copy));

    assertThat(program1.hashCode()).isEqualTo(program2.hashCode());
  }

  @Test
  void toString_ReturnsExpectedResult() {
    ProgramImpl program = new ProgramImpl(Set.of(ACTION_1, ACTION_2));

    assertThat(program.toString()).isEqualTo(("{ [1, 0, 2, 1, R], [2, 1, 1, 0, L] }"));
  }
}