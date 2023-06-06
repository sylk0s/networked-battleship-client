package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents a player's board
 */
public class Board extends DisplayableBoard {

  /**
   * The list of ships on this board
   */
  private final List<Ship> ships;

  /**
   * Constructor used to populate random ships
   *
   * @param width the width
   * @param height the height
   * @param map the map of ship type to count
   */

  public Board(int width, int height, Map<ShipType, Integer> map, Random random) {
    this(width, height, new ArrayList<>());
    setup(map, random);
  }

  public Board(int width, int height, Map<ShipType, Integer> map) {
    this(width, height, new ArrayList<>());
    setup(map, new Random());
  }

  /**
   * Constructor for a set amount of ships
   *
   * @param width the width
   * @param height the height
   * @param ships the list of ships
   */
  public Board(int width, int height, List<Ship> ships) {
    super(width, height);
    this.ships = ships;
  }

  /**
   * Removes any ships that have been destroyed
   */
  public void updateShips() {
    this.ships.removeIf(Ship::hadBeenSunk);
  }

  /**
   * Gets an optional ship at a coord
   *
   * @param c the coord to check
   * @return Some(ShipType) if a ship exists, otherwise None
   */
  private Optional<ShipType> shipTypeAt(Coord c) {
    for (Ship ship : this.ships) {
      if (ship.shipExistsAt(c)) {
        return Optional.of(ship.type());
      }
    }

    return Optional.empty();
  }

  /**
   * Sets up the ships on this board
   *
   * @param specifications the map of ShipType to integer
   */
  public void setup(Map<ShipType, Integer> specifications, Random random) {
    // for each ship type
    for (ShipType type : specifications.keySet().stream()
        .sorted(Comparator.comparingInt(ShipType::size).reversed())
        .collect(Collectors.toCollection(ArrayList::new))) {
      int size = type.size();

      // create n ships of a certain size
      for (int i = 0; i < specifications.get(type); i += 1) {
        placeShip(size, random);
      }
    }
  }

  /**
   * Place a ship on the board
   *
   * @param size The size of this ship
   */
  private void placeShip(int size, Random random) {
    // Tries random coords until one works
    ArrayList<Coord> coords = coordsOnBoard();
    Collections.shuffle(coords, random);
    for (Coord c : coords) {

      ArrayList<Coord> dirs = new ArrayList<>();
      dirs.add(new Coord(0, 1));
      dirs.add(new Coord(0, -1));
      dirs.add(new Coord(1, 0));
      dirs.add(new Coord(-1, 0));

      Collections.shuffle(dirs, random);
      // Tries each direction from these coords
      for (Coord dir : dirs) {
        // After a successful attempt each subsequent direction
        // will fail because there is already a ship at that location
        Optional<Ship> result = generateInDir(dir, c, size);
        if (result.isPresent()) {
          this.ships.add(result.get());
          return;
        }
      }
    }
    throw new IllegalStateException("No possible positions exist to place this ship");
  }

  /**
   * Get a list of each coord shuffled
   *
   * @return The list of all coords
   */
  public ArrayList<Coord> coordsOnBoard() {
    ArrayList<Coord> coords = new ArrayList<>();

    // Gets every coordinate on this board
    for (int i = 0; i < this.width; i += 1) {
      for (int j = 0; j < this.height; j += 1) {
        coords.add(new Coord(i, j));
      }
    }
    return coords;
  }

  /**
   * Get the type of the cell at a certain position
   *
   * @param c The position of the cell
   * @return The type of the cell
   */
  @Override
  protected CellType getCellType(Coord c) {
    if (this.shotsHit.contains(c)) {
      return CellType.HIT;
    }

    Optional<ShipType> ship = this.shipTypeAt(c);

    if (ship.isPresent()) {
      return switch (ship.get()) {
        case CARRIER -> CellType.CARRIER;
        case BATTLESHIP -> CellType.BATTLESHIP;
        case DESTROYER -> CellType.DESTROYER;
        case SUBMARINE -> CellType.SUBMARINE;
      };
    }

    if (this.shotsMissed.contains(c)) {
      return CellType.MISS;
    }

    return CellType.EMPTY;
  }

  /**
   * Attempts to generate a ship in a direction from an origin point
   *
   * @param dir The direction vector this ship will be generated in
   * @param origin The origin point of this ship
   * @param size The length of the ship
   *
   * @return An Optional representing the ship or a failed attempt
   */
  private Optional<Ship> generateInDir(Coord dir, Coord origin, int size) {
    Coord prev = origin;
    ArrayList<Coord> coords = new ArrayList<>();

    // for each place in the ship
    for (int i = 0; i < size; i += 1) {
      Coord finalPrev = prev;

      // Returns empty for an invalid position
      if (prev.outOfBound(new Coord(0, 0), new Coord(this.width, this.height))
          || this.ships.stream().anyMatch((ship) -> ship.shipExistsAt(finalPrev))) {
        return Optional.empty();
      }
      coords.add(prev);
      prev = prev.coordIn(dir);
    }

    // If all positions are valid return true
    return Optional.of(new Ship(coords));
  }

  /**
   * Shoots at a position
   *
   * @param c the position to shoot at
   * @return true if the shot is a hit
   */
  public boolean shootAt(Coord c) {
    ArrayList<Ship> shipAtLocation = this.ships.stream()
        .filter((ship) -> ship.shipExistsAt(c)).collect(Collectors.toCollection(ArrayList::new));

    shipAtLocation.forEach((ship) -> ship.fireAt(c));

    if (this.shipTypeAt(c).isEmpty()) {
      this.shotsMissed.add(c);
    } else {
      this.shotsHit.add(c);
    }

    return !shipAtLocation.isEmpty();
  }

  /**
   * Get all the ships
   *
   * @return the list of all ships
   */
  public List<Ship> getShips() {
    return this.ships;
  }
}
