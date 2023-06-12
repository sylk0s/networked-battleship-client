package cs3500.pa04;

import cs3500.pa03.model.AbstractPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.List;
import java.util.Map;

/**
 * A smarter AI player
 */
public class SmarterAiPlayer extends AbstractPlayer {

  /**
   * The probability board
   */
  private ProbabilityBoard probBoard;

  /**
   * The previous volley of shots
   */
  private List<Coord> prevVolley;


  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "smart ai";
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
    this.probBoard = new ProbabilityBoard(width, height, specifications);
    return super.setup(width, height, specifications);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> coords = this.probBoard.getMostProbableLocations(this.board.getShips().size());
    this.prevVolley = coords;
    return coords;
  }

  /**
   * Marks all the hits on this player
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : prevVolley) {
      if (shotsThatHitOpponentShips.contains(c)) {
        this.probBoard.markAsHit(c);
      } else {
        this.probBoard.markAsMiss(c);
      }
    }
    super.successfulHits(shotsThatHitOpponentShips);
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