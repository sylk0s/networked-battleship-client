package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstraction for the player to contain some common implementations and state
 */
public abstract class AbstractPlayer implements Player {
  /**
   * This player's board
   */

  protected Board board;

  /**
   * The opponent's board
   */
  protected OpponentBoard shotBoard;

  /**
   * Constructor
   */
  protected AbstractPlayer(Board board) {
    this.board = board;
    this.shotBoard = new OpponentBoard();
  }

  protected AbstractPlayer() {
    this.shotBoard = new OpponentBoard();
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int width, int height, Map<ShipType, Integer> map) {
    this.board = new Board(width, height, map);
    this.shotBoard = new OpponentBoard(width, height);
    return this.board.getShips();
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @return the placements of each ship on the board
   */
  public List<Ship> setup(int width, int height, Map<ShipType, Integer> map, Random random) {
    this.board = new Board(width, height, map, random);
    this.shotBoard = new OpponentBoard(width, height);
    return this.board.getShips();
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return filtered list of the shots that contain all locations of shots that hit a ship
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hits = new ArrayList<>();

    for (Coord shot : opponentShotsOnBoard) {
      if (board.shootAt(shot)) {
        hits.add(shot);
      }
    }
    this.board.updateShips();
    return hits;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    this.shotBoard.addHitShots(shotsThatHitOpponentShips);
  }
}
