package cs3500.pa03.controller;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.StupidAiPlayer;
import cs3500.pa03.view.Ui;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a game of battleship
 */
public class HumanVsAiGame implements Game {
  private final Player human;
  private final Player ai;

  private final UiController ui;
  private final Board humanBoard;
  private final Board aiBoard;

  /**
   * Create a new game with a Human vs an AI
   */
  public HumanVsAiGame(Ui ui, Random random) {
    this.ui = new UiController(ui);

    // Get the board size from user inputs
    Coord coord = this.ui.getBoardSize();
    int shipCount = Math.min(coord.getX(), coord.getY());

    // Get the ship types/counts from user input
    Map<ShipType, Integer> ships = this.ui.shipCounts(shipCount);

    // Interesting note, this means that you may be able to reverse engineer the LCG
    // to determine the ai's ship placements...
    this.humanBoard = new Board(coord.getX(), coord.getY(), ships, random);
    this.aiBoard = new Board(coord.getX(), coord.getY(), ships, random);

    // Setup and initialize the players
    this.human = new HumanPlayer(humanBoard, this.ui);
    this.ai = new StupidAiPlayer(aiBoard, random);

    // This is supposed to return a list of ships but that's lame and so im deciding not to use it
    this.human.setup(coord.getX(), coord.getY(), ships);
    this.ai.setup(coord.getX(), coord.getY(), ships);
  }

  /**
   * Run the game!
   */
  @Override
  public void run() {
    this.ui.displayEndMessage(this.runLoop());
  }

  /**
   * Runs the loop of the game
   *
   * @return A message indicating the result of the game
   */
  private String runLoop() {
    while (true) {
      //System.out.println("Human has " + humanBoard.getShips().size());
      //System.out.println("AI has " + aiBoard.getShips().size());
      // Determine if the game is over
      if (humanBoard.getShips().size() == 0 && aiBoard.getShips().size() == 0) {
        this.human.endGame(GameResult.TIE, "");
        this.ai.endGame(GameResult.TIE, "");
        return "The human and the AI tied!";
      } else if (humanBoard.getShips().size() == 0) {
        this.human.endGame(GameResult.LOSS, "");
        this.ai.endGame(GameResult.WIN, "");
        return "The human lost and the AI won.";
      } else if (aiBoard.getShips().size() == 0) {
        this.human.endGame(GameResult.WIN, "");
        this.ai.endGame(GameResult.LOSS, "");
        return "The human won and the AI lost.";
      }

      List<Coord> p1Shots = this.human.takeShots();
      List<Coord> p2Shots = this.ai.takeShots();

      this.human.successfulHits(this.ai.reportDamage(p1Shots));
      this.ai.successfulHits(this.human.reportDamage(p2Shots));
    }
  }
}
