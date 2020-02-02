package com.mistraltech.turingmachine.model;

public interface Action extends Comparable<Action> {

  State getState();

  Symbol getSymbol();

  State getNewState();

  Symbol getNewSymbol();

  Move getMove();
}
