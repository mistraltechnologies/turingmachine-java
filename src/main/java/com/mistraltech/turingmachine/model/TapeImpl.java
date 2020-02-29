package com.mistraltech.turingmachine.model;

import com.mistraltech.utils.CharSymbolUtils;
import com.mistraltech.utils.PStackUtils;
import com.mistraltech.utils.Preconditions;
import java.util.List;
import org.pcollections.ConsPStack;
import org.pcollections.PStack;

public class TapeImpl implements Tape {

  private final PStackUtils<Symbol> utils;
  private final Symbol blankSymbol;
  private final PStack<Symbol> leftString;
  private final PStack<Symbol> rightString;
  private final int index;

  private TapeImpl(Symbol blankSymbol) {
    this.utils = new PStackUtils<>(blankSymbol);
    this.blankSymbol = blankSymbol;
    this.leftString = ConsPStack.empty();
    this.rightString = ConsPStack.singleton(blankSymbol);
    this.index = 1;
  }

  private TapeImpl(Symbol blankSymbol, List<Symbol> initial) {
    this.utils = new PStackUtils<>(blankSymbol);
    this.blankSymbol = blankSymbol;
    this.leftString = ConsPStack.empty();
    this.rightString = ConsPStack.from(initial);
    this.index = 1;
  }

  private TapeImpl(Symbol blankSymbol, PStack<Symbol> leftString, PStack<Symbol> rightString, int index) {
    this.utils = new PStackUtils<>(blankSymbol);
    this.leftString = leftString;
    this.rightString = utils.pad(rightString, 1);
    this.blankSymbol = blankSymbol;
    this.index = index;
  }

  public static TapeImpl create(Symbol blankSymbol) {
    return new TapeImpl(blankSymbol);
  }

  public static TapeImpl create(Symbol blankSymbol, List<Symbol> symbols) {
    return new TapeImpl(blankSymbol, symbols);
  }

  public TapeImpl apply(Symbol newSymbol, Move move) {
    if (move == Move.LEFT) {
      return new TapeImpl(blankSymbol, tail(leftString), rightString.with(0, newSymbol).plus(head(leftString)),
          index - 1);
    }

    if (move == Move.RIGHT) {
      return new TapeImpl(blankSymbol, leftString.plus(newSymbol), tail(rightString), index + 1);
    }

    return new TapeImpl(blankSymbol, leftString, rightString.with(0, newSymbol), index);
  }

  private Symbol head(PStack<Symbol> string) {
    return string.isEmpty() ? blankSymbol : string.get(0);
  }

  private PStack<Symbol> tail(PStack<Symbol> string) {
    return string.isEmpty() ? string : string.subList(1);
  }

  public List<Symbol> getOutputString() {
    return calculateOutputString();
  }

  @Override
  public Symbol getCurrentSymbol() {
    return rightString.get(0);
  }

  private List<Symbol> calculateOutputString() {
    List<Symbol> outputString;

    if (index < 1) {
      // Discard (1 - index) elements from rightString then take valid output from rightString
      outputString = front(rightString.subList(-index + 1));
    } else {
      // take valid output from first (index - 1) elements of leftString plus currentSymbol plus rightString
      outputString = front(rightString.plusAll(leftString.subList(0, index - 1)));
    }

    return outputString;
  }

  private List<Symbol> front(PStack<Symbol> symbols) {
    // count how many symbols we want to take
    int index = 0;

    while (index < symbols.size() && symbols.get(index) != blankSymbol) {
      index++;
    }

    return symbols.subList(0, index);
  }

  private PStack<Symbol> infiniteSubList(PStack<Symbol> list, int from, int to) {
    PStack<Symbol> paddedList = (to > list.size()) ? utils.pad(list, to) : list;
    return paddedList.subList(from, to);
  }

  public List<Symbol> getString(int from, int to) {
    Preconditions.checkArgument(to >= from, "to cannot be less than from");

    int offsetFrom = from - index;
    int offsetTo = to - index;

    PStack<Symbol> fromLeft = infiniteSubList(leftString, Math.max(-offsetTo, 0), Math.max(-offsetFrom, 0));
    PStack<Symbol> fromRight = infiniteSubList(rightString, Math.max(offsetFrom, 0), Math.max(offsetTo, 0));

    return fromRight.plusAll(fromLeft);
  }

  @Override
  public String toString() {
    return "Tape [" + CharSymbolUtils.stringFromList(rightString.plusAll(leftString)) + "]";
  }
}
