package cs3500.pa03.model;

/**
 * Represents the different types a position on the board can have.
 */
public enum CellType {
  HIT(Colors.ANSI_GREEN, "*"),
  MISS(Colors.ANSI_RED, "-"),
  CARRIER(Colors.ANSI_YELLOW, "C"),
  BATTLESHIP(Colors.ANSI_CYAN, "B"),
  SUBMARINE(Colors.ANSI_PURPLE, "S"),
  DESTROYER(Colors.ANSI_BLUE, "D"),
  EMPTY(Colors.ANSI_WHITE, ".");

  /**
   * The string representation of this type
   */
  private final String string;
  private final String color;

  CellType(String color, String string) {
    this.string = string;
    this.color = color;
  }

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

  public String getWithoutFormat() {
    return this.string;
  }
}
