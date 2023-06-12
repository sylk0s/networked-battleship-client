package cs3500.pa04;

import cs3500.pa03.model.CellType;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.DisplayableBoard;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a board to store probabilities
 */
public class ProbabilityBoard extends DisplayableBoard {
  private static final Coord[] directions = {
      new Coord(0, 1),
      new Coord(0, -1),
      new Coord(1, 0),
      new Coord(-1, 0),
  };

  /**
   * The probabilities associated with the places on the board
   */
  private final double[][] board;

  /**
   * The ships and types on the board
   */
  private final Map<ShipType, Integer> sizes;

  /**
   * The shots this board has taken
   */
  private final ArrayList<Coord> shots;

  /**
   * constructor
   */
  public ProbabilityBoard(int width, int height, Map<ShipType, Integer> map) {
    super(width, height);
    this.sizes = map;
    this.shots = new ArrayList<>();
    this.board = new double[width][height];

    // initialize with zero
    for (int x = 0; x < width; x += 1) {
      for (int y = 0; y < height; y += 1) {
        this.board[x][y] = 0.;
      }
    }

    // update with initial weights
    this.updateBoard();
  }

  /**
   * Mark a coordinate as a hit
   *
   * @param c the coordinate to mark, assumed valid on the board
   */
  public void markAsHit(Coord c) {
    // sets the spot on the board as 0
    this.board[c.getX()][c.getY()] = 0;

    // sets the locations around it as 1
    if (c.getX() - 1 >= 0) {
      this.board[c.getX() - 1][c.getY()] = 1;
    }

    if (c.getX() + 1 < this.width) {
      this.board[c.getX() + 1][c.getY()] = 1;
    }

    if (c.getY() - 1 >= 0) {
      this.board[c.getX()][c.getY() - 1] = 1;
    }

    if (c.getY() + 1 < this.height) {
      this.board[c.getX()][c.getY() + 1] = 1;
    }

    // adds to the hit list
    this.shotsHit.add(c);
  }

  /**
   * Mark a position as a miss
   *
   * @param c The coordinate to mark, assumed as valid
   */
  public void markAsMiss(Coord c) {
    // sets the spot on the board as 0
    this.board[c.getX()][c.getY()] = 0;

    this.shotsMissed.add(c);

    // readjusts all positions around it to reflect the miss
    this.updateBoard();
  }

  /**
   * Get the top N most probable locations
   *
   * @param n the number of coords to get
   * @return The list of probable coords
   */
  public ArrayList<Coord> getNMostProbableLocations(int n) {
    ArrayList<CoordProbPair> pairs = new ArrayList<>();

    // creates the coord prob pair
    for (int x = 0; x < this.width; x += 1) {
      for (int y = 0; y < this.height; y += 1) {
        pairs.add(new CoordProbPair(new Coord(x, y), this.board[x][y]));
      }
    }

    pairs.sort(CoordProbPair::compareTo);

    ArrayList<Coord> result = pairs.stream().map(CoordProbPair::getCoord)
        .collect(Collectors.toCollection(ArrayList::new));
    result.removeIf(this.shots::contains);

    for (int i = result.size() - 1; i >= 0; i -= 1) {
      if (i >= n) {
        result.remove(i);
      }
    }

    this.shots.addAll(result);

    //System.out.println(result);
    return result;
  }

  /**
   * Updates the board with new probabilities
   */
  private void updateBoard() {
    for (int x = 0; x < this.width; x += 1) {
      for (int y = 0; y < this.height; y += 1) {
        this.board[x][y] = cellProb(new Coord(x, y));
      }
    }
  }

  /**
   * The probability of a specific cell
   *
   * @param c the coord of the cell
   * @return The probability
   */
  private double cellProb(Coord c) {

    // should never guess somewhere twice
    if (this.shotsHit.contains(c) || this.shotsMissed.contains(c)) {
      return 0;
    } else {

      // number of successful placements
      int count = 0;
      // Tried each direction
      for (Coord d : directions) {
        // for each ship type
        for (ShipType s : this.sizes.keySet()) {
          // if a ship can go there,
          if (tryPlace(s.size(), c, d)) {
            // add the number of successes plus the total number ships that exist for that number
            count += sizes.get(s);
          }
        }
      }

      // Some probability function proportional to the number of unique states
      return (double) count / (this.width * this.height * sizes.size()
          * sizes.values().stream().reduce(0, Integer::sum));
    }
  }

  /**
   * Test if a ship can be placed at a location
   *
   * @param length the remaining length
   * @param c the original position
   * @param dir the direction vector
   * @return a boolean determining if the ship can be placed at that location
   */
  private boolean tryPlace(int length, Coord c, Coord dir) {
    return !this.shotsMissed.contains(c)
        && (length <= 1 || tryPlace(length - 1, c.coordIn(dir), dir))
        && !c.outOfBound(new Coord(0,0), new Coord(this.width, this.height));
  }

  /**
   * Get the cells type at a position
   *
   * @param c The position of the cell
   * @return The CellType of the cell
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
}
