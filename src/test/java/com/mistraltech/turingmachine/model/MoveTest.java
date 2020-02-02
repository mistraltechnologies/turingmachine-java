package com.mistraltech.turingmachine.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class MoveTest {

  @Test
  void toString_ReturnsCode() {
    assertThat(Move.LEFT.toString()).isEqualTo("L");
    assertThat(Move.RIGHT.toString()).isEqualTo("R");
  }

  @Test
  void findByCode_WithValidCode_ReturnsOptionalOfMove() {
    assertThat(Move.findByCode('L')).hasValue(Move.LEFT);
    assertThat(Move.findByCode('R')).hasValue(Move.RIGHT);
  }

  @Test
  void getCode_ReturnsCode() {
    assertThat(Move.LEFT.getCode()).isEqualTo('L');
    assertThat(Move.RIGHT.getCode()).isEqualTo('R');
  }

  @Test
  void findByCode_WithUnknownCode_ReturnsEmpty() {
    Optional<Move> result = Move.findByCode('x');

    assertThat(result).isEmpty();
  }
}