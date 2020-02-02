package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.IntState;
import com.mistraltech.turingmachine.model.Program;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.turingmachine.model.builder.ProgramBuilder;
import com.mistraltech.turingmachine.model.builder.TuringMachineBuilder;
import com.mistraltech.turingmachine.model.factory.IntStateCharSymbolActionFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MachineLibrary {

  private static final IntStateCharSymbolActionFactory ACTION_FACTORY = new IntStateCharSymbolActionFactory();

  public static final TuringMachine CONS0 = createCons0TuringMachine();

  private static TuringMachine createCons0TuringMachine() {
    List<IntState> states = IntState.createClosedRangeFromZeroTo(3);
    Set<CharSymbol> inputSymbols = CharSymbol.toSymbols("01");
    Set<CharSymbol> symbols = mergeSets(inputSymbols, Set.of(CharSymbol.BLANK));

    Action[] actions = new Action[]{
        ACTION_FACTORY.createAction(0, '0', 1, '0', 'R'),
        ACTION_FACTORY.createAction(0, '1', 2, '0', 'R'),
        ACTION_FACTORY.createAction(0, '^', 3, '0', '0'),
        ACTION_FACTORY.createAction(1, '0', 1, '0', 'R'),
        ACTION_FACTORY.createAction(1, '1', 2, '0', 'R'),
        ACTION_FACTORY.createAction(1, '^', 3, '0', '0'),
        ACTION_FACTORY.createAction(2, '0', 1, '1', 'R'),
        ACTION_FACTORY.createAction(2, '1', 2, '1', 'R'),
        ACTION_FACTORY.createAction(2, '^', 3, '1', '0')
    };

    Program cons0 = ProgramBuilder.aProgram()
        .withActions(actions)
        .build();

    return TuringMachineBuilder.aTuringMachine()
        .withStates(states)
        .withFinalStates(Set.of(states.get(3)))
        .withInitialState(states.get(0))
        .withSymbols(symbols)
        .withInputSymbols(inputSymbols)
        .withBlankSymbol(CharSymbol.BLANK)
        .withProgram(cons0)
        .build();
  }

  private static Set<CharSymbol> mergeSets(Set<CharSymbol> set1, Set<CharSymbol> set2) {
    Set<CharSymbol> newSet = new HashSet<>(set1);
    newSet.addAll(set2);
    return Collections.unmodifiableSet(newSet);
  }

}
