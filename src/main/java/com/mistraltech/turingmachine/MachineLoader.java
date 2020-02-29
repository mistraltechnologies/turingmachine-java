package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.exceptions.ProgramLoadException;
import com.mistraltech.turingmachine.model.Action;
import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.Program;
import com.mistraltech.turingmachine.model.ProgramImpl;
import com.mistraltech.turingmachine.model.State;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.turingmachine.model.factory.IntStateCharSymbolActionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MachineLoader {

  private static final String CLASSPATH_PREFIX = "classpath:";
  private static final Pattern ACTION_PATTERN = Pattern
      .compile("\\((\\d+),\\s?'(.)'\\)\\s?->\\s?\\((\\d+),\\s?'(.)',\\s?([LR0])\\)");
  private static final IntStateCharSymbolActionFactory ACTION_FACTORY = new IntStateCharSymbolActionFactory();


  public TuringMachine read(String path) {
    List<Action> actions = readActions(path);

    if (actions.isEmpty()) {
      throw new ProgramLoadException("Invalid program - no actions");
    }

    Set<State> states = getStates(actions);
    Set<State> finalStates = getFinalStates(states, actions);
    State initialState = actions.get(0).getState();
    Set<Symbol> actionSymbols = getSymbols(actions);
    Set<Symbol> symbols = addSymbol(actionSymbols, CharSymbol.BLANK);
    Set<Symbol> inputSymbols = removeSymbol(actionSymbols, CharSymbol.BLANK);
    Symbol blankSymbol = CharSymbol.BLANK;
    Program program = new ProgramImpl(Set.copyOf(actions));

    return new TuringMachine(states, finalStates, initialState, symbols, inputSymbols, blankSymbol, program);
  }

  private Set<Symbol> removeSymbol(Set<Symbol> symbolSet, CharSymbol symbol) {
    HashSet<Symbol> reduced = new HashSet<>(symbolSet);
    reduced.remove(symbol);
    return reduced;
  }

  private Set<Symbol> addSymbol(Set<Symbol> symbolSet, CharSymbol symbol) {
    HashSet<Symbol> combined = new HashSet<>(symbolSet);
    combined.add(symbol);
    return combined;
  }

  private Set<State> getStates(Collection<Action> actions) {
    return actions.stream()
        .flatMap(a -> Stream.of(a.getState(), a.getNewState()))
        .collect(Collectors.toSet());
  }

  private Set<State> getFinalStates(Set<State> states, Collection<Action> actions) {
    Set<State> predecessorStates = actions.stream()
        .map(Action::getState)
        .collect(Collectors.toSet());

    return states.stream()
        .filter(s -> !predecessorStates.contains(s))
        .collect(Collectors.toSet());
  }

  private Set<Symbol> getSymbols(Collection<Action> actions) {
    return actions.stream()
        .flatMap(a -> Stream.of(a.getSymbol(), a.getNewSymbol()))
        .collect(Collectors.toSet());
  }

  private List<Action> readActions(String path) {
    Path resolvedPath = resolvePath(path);

    try (Stream<String> lines = Files.lines(resolvedPath, StandardCharsets.UTF_8)) {
      return lines
          .map(this::readAction)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new ProgramLoadException("Load failed", e);
    }
  }

  private Path resolvePath(String path) {
    if (path.startsWith(CLASSPATH_PREFIX)) {
      try {
        String resourceName = path.substring(CLASSPATH_PREFIX.length());
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
      } catch (URISyntaxException e) {
        throw new ProgramLoadException("Cannot get path " + path, e);
      }
    }

    return Paths.get(path);
  }

  private Optional<Action> readAction(String s) {
    String trimmed = s.trim();

    if (s.length() == 0 || s.startsWith("#")) {
      return Optional.empty();
    }

    Matcher actionMatcher = ACTION_PATTERN.matcher(trimmed);

    if (!actionMatcher.matches()) {
      throw new ProgramLoadException("Invalid line: " + s);
    }

    int state = Integer.parseInt(actionMatcher.group(1));
    char symbol = actionMatcher.group(2).charAt(0);
    int newState = Integer.parseInt(actionMatcher.group(3));
    char newSymbol = actionMatcher.group(4).charAt(0);
    char move = actionMatcher.group(5).charAt(0);

    return Optional.of(ACTION_FACTORY.createAction(state, symbol, newState, newSymbol, move));
  }
}
