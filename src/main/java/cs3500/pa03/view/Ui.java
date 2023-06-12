package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.DisplayableBoard;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a user interface for battleship
 */
public interface Ui {
  /**
   * Get the size of the board
   *
   * @param msg the message to display
   * @return A Coord where the X is the width, and Y is the height
   * @throws IOException Failing to print output to the output stream
   */
  Coord getBoardSize(String msg) throws IOException;

  /**
   * Get the numbers of each ship type
   *
   * @param msg the message to display
   * @return A map of the ShipType to the number of that ship
   * @throws IOException Failing to print output to the output stream
   */
  Map<ShipType, Integer> shipCounts(String msg) throws IOException;

  /**
   * Displays a board object
   *
   * @param b The board to display
   * @param desc A title for this board
   */
  void displayBoard(DisplayableBoard b, String desc);

  /**
   * Gets the shots this user will fire
   *
   * @param n The number of shots
   * @param msg the message to display
   * @return The list of coordinates the player chose to fire at
   * @throws IOException Failing to print output to the output stream
   */
  ArrayList<Coord> getShots(int n, String msg) throws IOException;

  /**
   * The message to display at the end of a game
   *
   * @param msg The message
   */
  void displayMessage(String msg);
}
