package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a board which contains the functionality to be printed
 */
public abstract class DisplayableBoard {
  /**
   * The shots that have hit on this board
   */
  protected final List<Coord> shotsHit;

  /**
   * The shots that have missed on this board
   */
  protected final List<Coord> shotsMissed;

  /**
   * The width of this board
   */
  protected final int width;

  /**
   * The height of this board
   */
  protected final int height;

  /**
   * Constructor
   *
   * @param width The width of the board
   * @param height The height of the board
   */
  protected DisplayableBoard(int width, int height) {
    this.shotsHit = new ArrayList<>();
    this.shotsMissed = new ArrayList<>();
    this.width = width;
    this.height = height;
  }

  /**
   * Get an array of all the positions on this board and the types of those positions
   *
   * @return An array of CellTypes representing the board
   */
  public CellType[][] coordTypes() {
    CellType[][] cells = new CellType[width][height];
    for (int i = 0; i < this.width; i += 1) {
      for (int j = 0; j < this.height; j += 1) {
        cells[i][j] = this.getCellType(new Coord(i, j));
      }
    }
    return cells;
  }

  /**
   * Get the cells type at a position
   *
   * @param c The position of the cell
   * @return The CellType of the cell
   */
  protected abstract CellType getCellType(Coord c);

  /**
   * Gets the height
   *
   * @return the height
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Gets the width
   *
   * @return the width
   */
  public int getWidth() {
    return this.width;
  }
}
