package cs3500.pa03.model;

/**
 * Possible types for a ship
 */
public enum ShipType {
  /**
   * A carrier ship
   */
  CARRIER(6),
  /**
   * A battleship
   */
  BATTLESHIP(5),
  /**
   * A destroyer
   */
  DESTROYER(4),
  /**
   * A submarine
   */
  SUBMARINE(3);

  /**
   * The size of this ship
   */
  private final int size;

  /**
   * constructor
   *
   * @param size the size of this ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the size of this ship
   *
   * @return the size of the ship
   */
  public int size() {
    return this.size;
  }
}