package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class TapeImplTest {

  private static final Symbol SYMBOL_BLANK = CharSymbol.BLANK;
  private static final Symbol SYMBOL_0 = CharSymbol.getSymbol('0');
  private static final Symbol SYMBOL_1 = CharSymbol.getSymbol('1');
  private static final Symbol SYMBOL_2 = CharSymbol.getSymbol('2');
  private static final Symbol SYMBOL_3 = CharSymbol.getSymbol('3');
  private static final Symbol SYMBOL_4 = CharSymbol.getSymbol('4');

  @Test
  void create_WithGivenBlankSymbol_CreatesEmptyTape() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    List<Symbol> string = tape.getString(1, 2);

    assertThat(string).isEqualTo(List.of(SYMBOL_BLANK));
  }

  @Test
  void apply_WithNewSymbolAndNoMove_ReturnsTapeWithUpdatedSymbol() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape.apply(SYMBOL_0, Move.NONE);

    List<Symbol> string = updatedTape.getString(1, 2);

    List<Symbol> expectedList = IntStream.range(1, 2)
        .mapToObj(i -> SYMBOL_0)
        .collect(Collectors.toList());

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithFromGreaterThanTo_ThrowsException() {
    TapeImpl tape = TapeImpl.create(SYMBOL_BLANK);

    assertThatThrownBy(() -> tape.getString(4, 3))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("to cannot be less than from");
  }

  @Test
  void getString_WithUnvisitedLocationsOnRight_ReturnsPaddedString() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape.apply(SYMBOL_0, Move.NONE);

    List<Symbol> string = updatedTape.getString(1, 4);

    assertThat(string).isEqualTo(List.of(SYMBOL_0, SYMBOL_BLANK, SYMBOL_BLANK));
  }

  @Test
  void getString_WithUnvisitedLocationsOnLeft_ReturnsPaddedString() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape.apply(SYMBOL_0, Move.NONE);

    List<Symbol> string = updatedTape.getString(-1, 2);

    List<Symbol> expectedList = List.of(SYMBOL_BLANK, SYMBOL_BLANK, SYMBOL_0);

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithNegativeIndex_ReturnsIndexAdjustedString() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape.apply(SYMBOL_0, Move.LEFT);

    List<Symbol> string = updatedTape.getString(1, 4);

    List<Symbol> expectedList = List.of(SYMBOL_0, SYMBOL_BLANK, SYMBOL_BLANK);

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithPopulatedLeftAndRightStrings_ReturnsString() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    // 4 3 2 1 0   (position 1 contains '2')
    //     ^
    TapeImpl updatedTape = tape
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_0, Move.LEFT)
        .apply(SYMBOL_1, Move.LEFT)
        .apply(SYMBOL_2, Move.LEFT)
        .apply(SYMBOL_3, Move.LEFT)
        .apply(SYMBOL_4, Move.NONE);

    List<Symbol> string = updatedTape.getString(-2, 5);

    List<Symbol> expectedList = List.of(SYMBOL_BLANK, SYMBOL_4, SYMBOL_3, SYMBOL_2, SYMBOL_1, SYMBOL_0, SYMBOL_BLANK);

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithFromEqualToTo_ReturnsEmptyList() {
    Tape tape = TapeImpl.create(CharSymbol.BLANK);

    List<Symbol> string = tape.getString(-3, -3);

    assertThat(string).isEmpty();
  }

  @Test
  void getString_WithNegativeRangeAndEmptyTape_ReturnsListOfBlanks() {
    Tape tape = TapeImpl.create(CharSymbol.BLANK);

    List<Symbol> string = tape.getString(-3, -1);

    List<Symbol> expectedList = IntStream.range(-3, -1)
        .mapToObj(i -> CharSymbol.BLANK)
        .collect(Collectors.toList());

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithPositiveRangeAndEmptyTape_ReturnsListOfBlanks() {
    Tape tape = TapeImpl.create(CharSymbol.BLANK);

    List<Symbol> string = tape.getString(3, 5);

    List<Symbol> expectedList = IntStream.range(3, 5)
        .mapToObj(i -> CharSymbol.BLANK)
        .collect(Collectors.toList());

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getString_WithNegativeFromAndPositiveToAndEmptyTape_ReturnsListOfBlanks() {
    Tape tape = TapeImpl.create(CharSymbol.BLANK);

    List<Symbol> string = tape.getString(-3, 3);

    List<Symbol> expectedList = IntStream.range(-3, 3)
        .mapToObj(i -> CharSymbol.BLANK)
        .collect(Collectors.toList());

    assertThat(string).isEqualTo(expectedList);
  }

  @Test
  void getOutputString_WithEmptyTape_ReturnsEmptyString() {
    Tape tape = TapeImpl.create(CharSymbol.BLANK);

    assertThat(tape.getOutputString()).isEmpty();
  }

  @Test
  void getOutputString_WithOutputToLeftOfHead_ReturnsOutput() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_1, Move.RIGHT)
        .apply(SYMBOL_2, Move.RIGHT)
        .apply(SYMBOL_3, Move.RIGHT)
        .apply(SYMBOL_4, Move.RIGHT);

    List<Symbol> output = updatedTape.getOutputString();

    List<Symbol> expectedList = List.of(SYMBOL_0, SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4);

    assertThat(output).isEqualTo(expectedList);
  }

  @Test
  void getOutputString_WithOutputToRightOfHead_ReturnsOutput() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_BLANK, Move.LEFT) // blank in position 3
        .apply(SYMBOL_1, Move.LEFT)     // 1 in position 2
        .apply(SYMBOL_2, Move.LEFT)     // 2 in position 1
        .apply(SYMBOL_3, Move.LEFT)     // 3 in position 0
        .apply(SYMBOL_4, Move.LEFT);    // 4 in position -1

    List<Symbol> output = updatedTape.getOutputString();

    List<Symbol> expectedList = List.of(SYMBOL_2, SYMBOL_1); // only symbols in position >= 1

    assertThat(output).isEqualTo(expectedList);
  }

  @Test
  void getOutputString_WithHeadAtPositionZero_ReturnsOutput() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_1, Move.LEFT)     // 1 in position 2
        .apply(SYMBOL_2, Move.LEFT)     // 2 in position 1
        .apply(SYMBOL_3, Move.NONE);    // 3 in position 0

    List<Symbol> output = updatedTape.getOutputString();

    List<Symbol> expectedList = List.of(SYMBOL_2, SYMBOL_1); // only symbols in position >= 1

    assertThat(output).isEqualTo(expectedList);
  }

  @Test
  void getOutputString_WithHeadAtPositionOne_ReturnsOutput() {
    TapeImpl tape = TapeImpl.create(CharSymbol.BLANK);

    TapeImpl updatedTape = tape
        .apply(SYMBOL_0, Move.RIGHT)
        .apply(SYMBOL_1, Move.LEFT)     // 1 in position 2
        .apply(SYMBOL_2, Move.LEFT)     // 2 in position 1
        .apply(SYMBOL_3, Move.RIGHT);   // 3 in position 0

    List<Symbol> output = updatedTape.getOutputString();

    List<Symbol> expectedList = List.of(SYMBOL_2, SYMBOL_1); // only symbols in position >= 1

    assertThat(output).isEqualTo(expectedList);
  }
}