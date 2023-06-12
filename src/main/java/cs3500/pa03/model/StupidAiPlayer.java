package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * An AI player that makes random guesses for where to shoot
 */
public class StupidAiPlayer extends AbstractPlayer {

  /**
   * The possible list of random shots for the player
   */
  private List<Coord> possibleShots;
  private final Random random;

  /**
   * Constructor
   *
   * @param board The board this player will use
   */
  public StupidAiPlayer(Board board, Random random) {
    super(board);
    this.possibleShots = new ArrayList<>();
    this.random = random;
  }

  public StupidAiPlayer(Board board) {
    this(board, new Random());
  }

  public StupidAiPlayer(Random random) {
    super();
    this.random = random;
  }

  public StupidAiPlayer() {
    this(new Random());
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "AI robot #42";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int width, int height, Map<ShipType, Integer> specifications) {
    List<Ship> result = super.setup(width, height, specifications);
    this.possibleShots = this.board.coordsOnBoard();
    return result;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int width, int height,
                          Map<ShipType, Integer> specifications, Random random) {
    List<Ship> result = super.setup(width, height, specifications, random);
    this.possibleShots = this.board.coordsOnBoard();
    return result;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    Collections.shuffle(this.possibleShots, this.random);
    int size = Math.min(this.board.getShips().size(), this.shotBoard.remainingSpaces());
    List<Coord> result = new ArrayList<>();
    for (int i = 0; i < size; i += 1) {
      result.add(this.possibleShots.remove(0));
    }
    this.shotBoard.addAllToMissed(result);
    return result;
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    System.out.println(result + " " + reason);
  }
}
