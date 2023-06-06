package cs3500.pa03.model;

/**
 * Possible types for a ship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private final int size;

  ShipType(int size) {
    this.size = size;
  }

  public int size() {
    return this.size;
  }
}