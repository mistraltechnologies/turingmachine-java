package com.mistraltech.turingmachine.model;

import com.mistraltech.utils.CharSymbolUtils;
import com.mistraltech.utils.PersistentStack;
import com.mistraltech.utils.PersistentStackImpl;
import com.mistraltech.utils.Preconditions;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TapeImpl implements Tape {

  private final Symbol blankSymbol;
  private final PersistentStack<Symbol> leftString;
  private final PersistentStack<Symbol> rightString;
  private final int index;

  private TapeImpl(Symbol blankSymbol) {
    this.blankSymbol = blankSymbol;
    this.leftString = PersistentStackImpl.empty();
    this.rightString = PersistentStackImpl.singleton(blankSymbol);
    this.index = 1;
  }

  private TapeImpl(Symbol blankSymbol, List<Symbol> initial) {
    this.blankSymbol = blankSymbol;
    this.leftString = PersistentStackImpl.empty();
    this.rightString = PersistentStackImpl.from(initial);
    this.index = 1;
  }

  private TapeImpl(Symbol blankSymbol, PersistentStack<Symbol> leftString, PersistentStack<Symbol> rightString, int index) {
    this.leftString = leftString;
    this.rightString = rightString.pad(blankSymbol, 1);
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
      return new TapeImpl(blankSymbol, tail(leftString), rightString.pop().push(newSymbol).push(head(leftString)),
          index - 1);
    }

    if (move == Move.RIGHT) {
      return new TapeImpl(blankSymbol, leftString.push(newSymbol), tail(rightString), index + 1);
    }

    return new TapeImpl(blankSymbol, leftString, rightString.pop().push(newSymbol), index);
  }

  private Symbol head(PersistentStack<Symbol> string) {
    return string.isEmpty() ? blankSymbol : string.read();
  }

  private PersistentStack<Symbol> tail(PersistentStack<Symbol> stack) {
    return stack.isEmpty() ? stack : stack.pop();
  }

  public List<Symbol> getOutputString() {
    return calculateOutputString();
  }

  @Override
  public Symbol getCurrentSymbol() {
    return rightString.read();
  }

  private List<Symbol> calculateOutputString() {
    List<Symbol> outputString;

    if (index < 1) {
      // Discard (1 - index) elements from rightString then take valid output from rightString
      outputString = front(rightString.pop(-index + 1));
    } else {
      // take valid output from first (index - 1) elements of leftString plus currentSymbol plus rightString
      outputString = front(rightString.pushAll(leftString.truncate(index - 1)));
    }

    return outputString;
  }

  private List<Symbol> front(PersistentStack<Symbol> symbols) {
    List<Symbol> frontList = new ArrayList<>();

    Optional<Symbol> s = symbols.peek();
    while(s.isPresent() && s.get() != blankSymbol) {
      frontList.add(s.get());
      symbols = symbols.pop();
      s = symbols.peek();
    }

    return frontList;
  }

  private List<Symbol> asList(PersistentStack<Symbol> symbols) {
    List<Symbol> list = new ArrayList<>();

    Optional<Symbol> s = symbols.peek();
    while(s.isPresent()) {
      list.add(s.get());
      symbols = symbols.pop();
      s = symbols.peek();
    }

    return list;
  }

  private PersistentStack<Symbol> infiniteSubList(PersistentStack<Symbol> list, int from, int to) {
    PersistentStack<Symbol> paddedList = (to > list.size()) ? list.pad(blankSymbol, to) : list;
    return paddedList.pop(from).truncate(to - from);
  }

  public List<Symbol> getString(int from, int to) {
    Preconditions.checkArgument(to >= from, "to cannot be less than from");

    int offsetFrom = from - index;
    int offsetTo = to - index;

    PersistentStack<Symbol> fromLeft = infiniteSubList(leftString, Math.max(-offsetTo, 0), Math.max(-offsetFrom, 0));
    PersistentStack<Symbol> fromRight = infiniteSubList(rightString, Math.max(offsetFrom, 0), Math.max(offsetTo, 0));

    return asList(fromRight.pushAll(fromLeft));
  }

  @Override
  public String toString() {
    return "Tape [" + CharSymbolUtils.symbolListToString(asList(rightString.pushAll(leftString))) + "]";
  }
}
