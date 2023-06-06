package cs3500.pa04;

import cs3500.pa03.controller.Game;
import cs3500.pa03.controller.HumanVsAiGame;
import cs3500.pa03.view.Cli;
import cs3500.pa03.view.Ui;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Ui ui = new Cli(new InputStreamReader(System.in), new PrintStream(System.out));
    Game game = new HumanVsAiGame(ui, new Random());
    game.run();
  }
}