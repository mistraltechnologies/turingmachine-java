package com.mistraltech.turingmachine;

import com.mistraltech.turingmachine.model.CharSymbol;
import com.mistraltech.turingmachine.model.Symbol;
import com.mistraltech.turingmachine.model.Tape;
import com.mistraltech.turingmachine.model.TapeImpl;
import com.mistraltech.turingmachine.model.TuringMachine;
import com.mistraltech.utils.CharSymbolUtils;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class App {

  private final String programFilePathName;
  private final String input;
  private final MachineLoader machineLoader = new MachineLoader();

  public App(String programFilePathName, String input) {
    this.programFilePathName = programFilePathName;
    this.input = input;
  }

  /**
   * Usage: App program_file tape_file.
   *
   * @param args program arguments
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      usage();
      System.exit(1);
    }

    new App(args[0], args[1]).run();
  }

  private static void usage() {
    System.out.println("Usage: App <program_file> <input>");
  }

  /**
   * Run the application.
   */
  public void run() {
    Tape inputTape = TapeImpl.create(CharSymbol.BLANK, CharSymbolUtils.listFromString(input));

    System.out.println("Input:");
    System.out.println(input);
    System.out.println();

    TuringMachine tm = machineLoader.read(programFilePathName);

    Output output = new TuringMachineSimulatorImpl(tm).compute(inputTape);

    System.out.println("Output:");
    System.out.println(CharSymbolUtils.stringFromList(output.getOutputString()));
  }
}
