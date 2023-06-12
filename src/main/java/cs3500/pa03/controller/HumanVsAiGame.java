package cs3500.pa03.controller;

import cs3500.pa03.model.AbstractPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.StupidAiPlayer;
import cs3500.pa03.view.Ui;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a game of battleship
 */
public class HumanVsAiGame implements Game {
  private final AbstractPlayer human;
  private final AbstractPlayer ai;

  private final UiController ui;
  private final List<Coord> humanShipCoords;
  private final List<Coord> aiShipCoords;

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

    // Setup and initialize the players
    this.human = new HumanPlayer(new Board(coord.getX(), coord.getY(), ships), this.ui);
    this.ai = new StupidAiPlayer(new Board(coord.getX(), coord.getY(), ships), random);

    List<Ship> humShips = this.human.setup(coord.getX(), coord.getY(), ships, random);
    List<Ship> aiShips = this.ai.setup(coord.getX(), coord.getY(), ships, random);

    this.humanShipCoords = new ArrayList<>();
    this.aiShipCoords = new ArrayList<>();

    humShips.forEach((ship) -> this.humanShipCoords.addAll(ship.coords()));
    aiShips.forEach((ship) -> this.aiShipCoords.addAll(ship.coords()));
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

      if (this.aiShipCoords.size() == 0 && this.humanShipCoords.size() == 0) {
        this.human.endGame(GameResult.TIE, "");
        this.ai.endGame(GameResult.TIE, "");
        return "The human and the AI tied!";
      } else if (this.humanShipCoords.size() == 0) {
        this.human.endGame(GameResult.LOSS, "");
        this.ai.endGame(GameResult.WIN, "");
        return "The human lost and the AI won.";
      } else if (this.aiShipCoords.size() == 0) {
        this.human.endGame(GameResult.WIN, "");
        this.ai.endGame(GameResult.LOSS, "");
        return "The human won and the AI lost.";
      }

      // gets shots from the players
      List<Coord> p1Shots = this.human.takeShots();
      List<Coord> p2Shots = this.ai.takeShots();

      // removes any hit positions to check for win
      this.humanShipCoords.removeIf(p1Shots::contains);
      this.aiShipCoords.removeIf(p2Shots::contains);

      // reports damage to each other
      this.human.successfulHits(this.ai.reportDamage(p1Shots));
      this.ai.successfulHits(this.human.reportDamage(p2Shots));
    }
  }
}
