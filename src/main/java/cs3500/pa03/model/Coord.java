package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents some point in a 2D space
 */
public class Coord {
  /**
   * The x position of this coord
   */
  private final int positionX;

  /**
   * The y position of this coord
   */
  private final int positionY;

  /**
   * Constructor
   *
   * @param positionX the x coord
   * @param positionY the y coord
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int positionX, @JsonProperty("y") int positionY) {
    this.positionX = positionX;
    this.positionY = positionY;
  }

  /**
   * Determines if another coord is the same as this coord
   *
   * @param that the other coord
   * @return true if they are the same
   */
  public boolean sameCoord(Coord that) {
    return this.positionX == that.positionX && this.positionY == that.positionY;
  }

  /**
   * Gets the x coord
   *
   * @return the x coord
   */
  @JsonGetter("x")
  public int getX() {
    return this.positionX;
  }

  /**
   * Gets the y coord
   *
   * @return the y coord
   */
  @JsonGetter("y")
  public int getY() {
    return this.positionY;
  }

  /**
   * Determine if a coord equals this coord
   *
   * @param that Tte other coord
   * @return true if they are equivalent coords
   */
  @Override
  public boolean equals(Object that) {
    return that instanceof Coord && ((Coord) that).sameCoord(this);
  }

  /**
   * Get the vector addition of two coords
   *
   * @param c the other coord
   * @return A new coord representing the endpoint
   */
  public Coord coordIn(Coord c) {
    return new Coord(this.positionX + c.positionX, this.positionY + c.positionY);
  }

  /**
   * Determines if this coord is out of a min/max bound
   *
   * @param min The minimum bound
   * @param max The maximum bound
   * @return true if the coord is outside of the bound
   */
  public boolean outOfBound(Coord min, Coord max) {
    return this.positionY >= max.positionY
        || this.positionY < min.positionY
        || this.positionX >= max.positionX
        || this.positionX < min.positionX;
  }

  public String toString() {
    return "{ " + this.positionX + ", " + this.positionY + " }";
  }
}
