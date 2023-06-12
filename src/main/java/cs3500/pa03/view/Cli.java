package cs3500.pa03.view;

import cs3500.pa03.model.CellType;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.DisplayableBoard;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a CLI the player uses to interact with the game
 */
public class Cli implements Ui {

  /**
   * The scanner for input from the user
   */
  private final Scanner sc;

  /**
   * The appendable output to the user
   */
  private final Appendable output;

  /**
   * Constructor
   *
   * @param input User input
   * @param output Output to user
   */

  public Cli(Readable input, Appendable output) {
    this.sc = new Scanner(input);
    this.output = output;
  }

  /**
   * Get the size of this board from the user
   *
   * @param msg THe prompt to display
   * @return The size of the board
   * @throws IOException Failing to output to the user
   */
  @Override
  public Coord getBoardSize(String msg) throws IOException {
    this.output.append(msg);
    String line = this.sc.nextLine();
    String[] args = line.split(" ");

    if (args.length == 2) {
      try {
        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        return new Coord(width, height);
      } catch (NumberFormatException e) {
        throw new IOException();
      }
    } else {
      throw new IOException();
    }
  }

  /**
   * Gets the counts of different ship types from the user
   *
   * @param msg The prompt to display
   * @return A Map of ShipType to it's count
   * @throws IOException Failing to output to the user
   */
  @Override
  public Map<ShipType, Integer> shipCounts(String msg) throws IOException {
    this.output.append(msg);
    String line = this.sc.nextLine();
    String[] args = line.split(" ");
    if (args.length == 4) {
      Map<ShipType, Integer> sizes = new HashMap<>();
      for (int i = 0; i < args.length; i += 1) {
        try {
          sizes.put(ShipType.values()[i], Integer.parseInt(args[i]));
        } catch (NumberFormatException e) {
          throw new IOException();
        }
      }
      return sizes;
    } else {
      throw new IOException();
    }
  }

  /**
   * Displays the board to the user
   *
   * @param b The board to display
   * @param desc A title for this board
   */
  @Override
  public void displayBoard(DisplayableBoard b, String desc) {
    int width = b.getWidth();
    int height = b.getHeight();
    CellType[][] cells = b.coordTypes();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        sb.append(cells[j][i].toString()).append(" ");
      }
      sb.append("\n");
    }
    try {
      this.output.append(desc).append("\n");
      this.output.append(sb.toString());
    } catch (IOException e) {
      System.err.println("Failed to print " + sb);
    }
  }

  /**
   * Display the board without color formatting... mostly for testing
   *
   * @param b the board to display
   */
  public void displayBoardNoFormat(DisplayableBoard b) {
    int width = b.getWidth();
    int height = b.getHeight();
    CellType[][] cells = b.coordTypes();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {
        sb.append(cells[j][i].getWithoutFormat()).append(" ");
      }
      sb.append("\n");
    }
    try {
      this.output.append(sb.toString());
    } catch (IOException e) {
      System.err.println("Failed to print " + sb);
    }
  }

  /**
   * Gets a list of shots from the user
   *
   * @param n The number of shots
   * @param msg The message to display
   * @return The list of shots
   * @throws IOException Failing to print output to the user
   */
  @Override
  public ArrayList<Coord> getShots(int n, String msg) throws IOException {
    this.output.append(msg);
    ArrayList<Coord> coords = new ArrayList<>();
    for (int i = 0; i < n; i += 1) {
      String line = this.sc.nextLine();
      String[] args = line.split(" ");

      if (args.length == 2) {
        try {
          int x = Integer.parseInt(args[0]);
          int y = Integer.parseInt(args[1]);
          coords.add(new Coord(x, y));
        } catch (NumberFormatException e) {
          throw new IOException();
        }
      } else {
        throw new IOException();
      }
    }
    return coords;
  }

  /**
   * The message to display at the end of a game
   *
   * @param msg The message
   */
  @Override
  public void displayMessage(String msg) {
    try {
      this.output.append(msg);
    } catch (IOException e) {
      System.err.println("Failed to print " + msg);
    }
  }
}
