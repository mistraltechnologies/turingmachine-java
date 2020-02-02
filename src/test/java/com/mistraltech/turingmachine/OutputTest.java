package com.mistraltech.turingmachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.Configuration;
import com.mistraltech.turingmachine.model.IntState;
import com.mistraltech.turingmachine.model.Move;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.turingmachine.model.TapeImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class OutputTest {

  private static final TapeImpl BLANK_TAPE = TapeImpl.create(CharSymbol.BLANK);
  private static final IntState STATE_0 = IntState.getState(0);
  private static final Symbol SYMBOL_1 = CharSymbol.getSymbol('1');

  @Test
  void haltsAndSucceeds_WithGivenConfiguration_ReturnsOutputWhereSucceededReturnsTrue() {
    Configuration configuration = new Configuration(STATE_0, BLANK_TAPE);

    Output output = Output.haltsAndSucceeds(configuration);

    assertThat(output.succeeded()).isTrue();
  }

  @Test
  void haltsAndSucceeds_WithGivenConfiguration_ReturnsOutputWhereGetOutputStringReturnsResult() {
    Configuration configuration = new Configuration(STATE_0, BLANK_TAPE.apply(SYMBOL_1, Move.RIGHT));

    Output output = Output.haltsAndSucceeds(configuration);

    assertThat(output.getOutputString()).isEqualTo(List.of(SYMBOL_1));
  }

  @Test
  void haltsAndFails_ReturnsOutputWhereSucceededReturnsFalse() {
    Output output = Output.haltsAndFails();

    assertThat(output.succeeded()).isFalse();
  }

  @Test
  void haltsAndFails_ReturnsOutputWhereGetOutputStringThrowsException() {
    Output output = Output.haltsAndFails();

    assertThatThrownBy(output::getOutputString)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No output available");
  }
}