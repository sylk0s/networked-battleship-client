package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.Orientation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a ship on the board in the game battleship
 */

public class Ship {
  /**
   * The positions on the board this ship occupies
   */
  private final List<Coord> positions;

  /**
   * The positions in this ship that has been hit
   */
  private final List<Coord> hitPositions;

  /**
   * Constructor
   *
   * @param positions The positions on the board this ship exists in
   */
  public Ship(List<Coord> positions) {
    this.positions = positions;
    this.hitPositions = new ArrayList<>();
  }

  /**
   * Create a ship from a json
   *
   * @param len the length of the ship
   * @param or the orientation of the ship
   * @param c the origin coordinate of this ship
   */
  @JsonCreator
  public Ship(@JsonProperty("length") int len,
              @JsonProperty("direction") Orientation or,
              @JsonProperty("coord") Coord c) {

    Coord directionVec = switch (or) {
      case HORIZONTAL -> new Coord(1, 0);
      case VERTICAL -> new Coord(0, 1);
    };

    this.hitPositions = new ArrayList<>();
    this.positions = new ArrayList<>();

    for (int i = 0; i < len; i += 1) {
      this.positions.add(c);
      c = c.coordIn(directionVec);
    }
  }

  /**
   * If this ship has been sunk
   *
   * @return A boolean that will be true if this ship has been sunk
   */
  public boolean hadBeenSunk() {
    // Intentionally ignoring this warning because it literally changes the programs behavior
    return this.hitPositions.containsAll(this.positions);
  }

  /**
   * Firing a shot at this ship
   *
   * @param c The position of the shot
   */
  public void fireAt(Coord c) {
    this.hitPositions.add(c);
  }

  /**
   * Determines if a specified coord is in occupied by this ship
   *
   * @param c The specified coord
   * @return true of the ship exists in c
   */
  public boolean shipExistsAt(Coord c) {
    return this.positions.stream()
        .anyMatch(c::sameCoord);
  }

  /**
   * Returns the type of this ship
   *
   * @return A ShipType representing the type of this ship
   */
  public ShipType type() {
    return switch (this.positions.size()) {
      case 6 -> ShipType.CARRIER;
      case 5 -> ShipType.BATTLESHIP;
      case 4 -> ShipType.DESTROYER;
      case 3 -> ShipType.SUBMARINE;
      default -> {
        System.out.println(this.positions.size());
        throw new IllegalStateException("Invalid ship size");
      }
    };
  }

  /**
   * Get the length of this ship
   * @return the length of this ship
   */
  @JsonGetter("length")
  public int length() {
    return this.positions.size();
  }

  /**
   * Get the direction of this ship
   *
   * @return the direction of this ship
   */
  @JsonGetter("direction")
  public Orientation direction() {
    if (mapSame(this.positions, Coord::getX)) {
      return Orientation.VERTICAL;
    } else if (mapSame(this.positions, Coord::getY)) {
      return Orientation.HORIZONTAL;
    } else {
      throw new IllegalStateException("Ship somehow was diagonal");
    }
  }

  /**
   * Get the origin coord of this ship
   *
   * @return the origin coord of this ship
   */
  @JsonGetter("coord")
  public Coord leastCoord() {
    return switch (this.direction()) {
      case HORIZONTAL -> this.positions.stream()
          .reduce(new Coord(Integer.MAX_VALUE, Integer.MAX_VALUE),
              (a, b) -> a.getX() > b.getX() ? b : a);
      case VERTICAL -> this.positions.stream()
          .reduce(new Coord(Integer.MAX_VALUE, Integer.MAX_VALUE),
              (a, b) -> a.getY() > b.getY() ? b : a);
    };
  }

  /**
   * Checks how many unique mapped values there are
   *
   * @param list the original list
   * @param f the function to map
   * @return if the mapped values are the same
   * @param <T> the output type
   * @param <U> the input list type
   */
  private <T, U> boolean mapSame(List<U> list, Function<U, T> f) {
    return list.stream()
        .map(f).distinct().limit(2).count() <= 1;
  }

  /**
   * Determine if two ships are equal
   *
   * @param that the other ship
   * @return true if the two ships are equal
   */
  public boolean equals(Object that) {
    return that instanceof Ship
        && ((Ship) that).positions.equals(this.positions);
  }

  /**
   * Get all the coords in this ship
   *
   * @return the list of all coords in this ship
   */
  public List<Coord> coords() {
    return this.positions;
  }
}