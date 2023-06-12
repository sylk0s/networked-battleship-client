package cs3500.pa03.model;

import java.util.List;

/**
 * Represents a player's view of an opponents board
 */
public class OpponentBoard extends DisplayableBoard {

  /**
   * Constructor
   *
   * @param width the width of the board
   * @param height the height of the board
   */
  public OpponentBoard(int width, int height) {
    super(width, height);
  }

  /**
   * Constructor that fills with dummy info
   */
  public OpponentBoard() {
    super(0, 0);
  }

  /**
   * Get the type of cell at positions c
   *
   * @param c The position
   * @return The type of cell at this position
   */
  @Override
  protected CellType getCellType(Coord c) {
    if (this.shotsHit.contains(c)) {
      return CellType.HIT;
    } else if (this.shotsMissed.contains(c)) {
      return CellType.MISS;
    } else {
      return CellType.EMPTY;
    }
  }

  /**
   * Adds hit shots to this board
   * Called after addAllToMissed since this removes the shots that hit from missed
   *
   * @param coords The list of shots that have been hit
   */
  public void addHitShots(List<Coord> coords) {
    for (Coord c : coords) {
      this.shotsMissed.remove(c);
      this.shotsHit.add(c);
    }
  }

  /**
   * The number of remaining spaces on this board
   *
   * @return the remaining spaces
   */
  public int remainingSpaces() {
    return this.height * this.width - this.shotsMissed.size() - this.shotsHit.size();
  }

  /**
   * Add all shots that missed
   * This is called before addHitShots since that function removes any that ended up hitting
   *
   * @param coords The list of shots the salvo fired
   */
  public void addAllToMissed(List<Coord> coords) {
    this.shotsMissed.addAll(coords);
  }
}
