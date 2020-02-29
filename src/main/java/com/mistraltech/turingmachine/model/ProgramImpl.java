package com.mistraltech.turingmachine.model;

import static com.mistraltech.utils.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class ProgramImpl implements Program {

  private final Map<ActionKey, Action> actions = new HashMap<>();

  public ProgramImpl(Set<Action> actions) {
    checkArgument(actions != null, "actions must not be null");

    actions.forEach(this::checkUniqueAndRegister);
  }

  private void checkUniqueAndRegister(Action action) {
    ActionKey key = createKey(action);

    Action previous = actions.putIfAbsent(key, action);

    if (previous != null) {
      throw new IllegalArgumentException(String.format(
          "state and symbol combination must be unique - multiple actions with state '%s' and symbol '%s'",
          action.getState(), action.getSymbol()));
    }
  }

  private ActionKey createKey(Action action) {
    return new ActionKey(action.getState(), action.getSymbol());
  }

  @Override
  public Optional<Action> apply(State state, Symbol symbol) {
    return Optional.ofNullable(actions.get(new ActionKey(state, symbol)));
  }

  @Override
  public String toString() {
    return actions.values().stream()
        .sorted()
        .map(Object::toString)
        .collect(Collectors.joining(", ", "{ ", " }"));
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    ProgramImpl program = (ProgramImpl) other;

    return actions.equals(program.actions);
  }

  @Override
  public int hashCode() {
    return actions.hashCode();
  }

  private static final class ActionKey {

    private final State state;
    private final Symbol symbol;

    public ActionKey(State state, Symbol symbol) {
      this.state = state;
      this.symbol = symbol;
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") // Don't need the full set of checks here
    public boolean equals(Object other) {
      ActionKey actionKey = (ActionKey) other;
      return state.equals(actionKey.state) && symbol.equals(actionKey.symbol);
    }

    @Override
    public int hashCode() {
      int result = state.hashCode();
      result = 31 * result + symbol.hashCode();
      return result;
    }
  }
}
