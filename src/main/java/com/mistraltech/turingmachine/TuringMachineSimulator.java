package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.model.Tape;

public interface TuringMachineSimulator {

  Output compute(Tape input);

  Output compute(Tape input, int maxIterations);
}
