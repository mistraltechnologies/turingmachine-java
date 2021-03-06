package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.exceptions.DidNotHaltException;
import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.Configuration;
import com.mistraltech.turingmachine.model.State;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.turingmachine.model.Tape;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.utils.PersistentStack;
import com.mistraltech.utils.PersistentStackImpl;
import com.mistraltech.utils.Preconditions;
import java.util.Optional;

public class TuringMachineSimulatorImpl implements TuringMachineSimulator {

  private static final int DEFAULT_MAX_ITERATIONS = 1000;

  private final TuringMachine turingMachine;

  public TuringMachineSimulatorImpl(TuringMachine turingMachine) {
    this.turingMachine = turingMachine;
  }

  @Override
  public Output compute(Tape input) {
    return compute(input, DEFAULT_MAX_ITERATIONS);
  }

  @Override
  public Output compute(Tape input, int maxIterations) {
    Preconditions.checkArgument(input != null, "input must not be null");

    PersistentStack<Configuration> configurations = PersistentStackImpl.singleton(getInitialConfiguration(input));

    while (configurations.size() <= maxIterations) {
      Configuration currentConfiguration = configurations.read();
      State currentState = currentConfiguration.getState();

      if (turingMachine.isFinalState(currentState)) {
        return Output.haltsAndSucceeds(currentConfiguration);
      }

      Tape currentTape = currentConfiguration.getTape();
      Symbol currentSymbol = currentTape.getCurrentSymbol();
      Optional<Action> maybeAction = turingMachine.findAction(currentState, currentSymbol);

      if (maybeAction.isEmpty()) {
        return Output.haltsAndFails();
      }

      Configuration newConfiguration = getNewConfiguration(maybeAction.get(), currentTape);
      configurations = configurations.push(newConfiguration);
    }

    throw new DidNotHaltException(maxIterations);
  }

  private Configuration getInitialConfiguration(Tape input) {
    return new Configuration(turingMachine.getInitialState(), input);
  }

  private Configuration getNewConfiguration(Action action, Tape currentTape) {
    return new Configuration(action.getNewState(), currentTape.apply(action.getNewSymbol(), action.getMove()));
  }
}
