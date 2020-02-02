package com.mistraltech.turingmachine.model.builder;

import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.Program;
import com.mistraltech.turingmachine.model.ProgramImpl;
import java.util.Collection;
import java.util.Set;

public final class ProgramBuilder {

  private Set<Action> actions;

  private ProgramBuilder() {
  }

  public static ProgramBuilder aProgram() {
    return new ProgramBuilder();
  }

  public ProgramBuilder withActions(Action... actions) {
    this.actions = Set.of(actions);
    return this;
  }

  public ProgramBuilder withActions(Collection<Action> actions) {
    this.actions = Set.copyOf(actions);
    return this;
  }

  public Program build() {
    return new ProgramImpl(actions);
  }
}
