package cs3500.pa03.model;

/**
 * Represents the different types a position on the board can have.
 */
public enum CellType {
  /**
   * A hit on the board
   */
  HIT(Colors.ANSI_GREEN, "*"),
  /**
   * A miss on the board
   */
  MISS(Colors.ANSI_RED, "-"),
  /**
   * A carrier occupying a cell
   */
  CARRIER(Colors.ANSI_YELLOW, "C"),
  /**
   * A battleship occupying a cell
   */
  BATTLESHIP(Colors.ANSI_CYAN, "B"),
  /**
   * A submarine occupying a cell
   */
  SUBMARINE(Colors.ANSI_PURPLE, "S"),
  /**
   * A destroyer occupying a cell
   */
  DESTROYER(Colors.ANSI_BLUE, "D"),
  /**
   * Nothing exists in this cell
   */
  EMPTY(Colors.ANSI_WHITE, ".");

  /**
   * The string representation of this type
   */
  private final String string;

  /**
   * The color for this type of cell
   */
  private final String color;

  /**
   * Constructor
   *
   * @param color the Color of this
   * @param string the string that represents this cell
   */
  CellType(String color, String string) {
    this.string = string;
    this.color = color;
  }

  /**
   * Converts this cell to a string
   *
   * @return the string representation of this cell
   */
  @Override
  public String toString() {
    String name = System.getProperty("os.name");

    // Fancy coloring for *nix systems <3
    if (name.contains("nux") || name.contains("mac")) {
      return this.color + this.string + Colors.ANSI_RESET;

      // Lame coloring for windows (eww)
    } else {
      return this.string;
    }
  }

  /**
   * The string representation but without the color no matter the operating system
   *
   * @return the string representation of this cell
   */
  public String getWithoutFormat() {
    return this.string;
  }
}
