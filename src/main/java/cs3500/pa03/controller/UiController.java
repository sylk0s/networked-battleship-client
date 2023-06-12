package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.DisplayableBoard;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Ui;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A Controller for the Ui
 */
public class UiController {
  /**
   * The list of guesses so far
   */
  private final ArrayList<Coord> guesses = new ArrayList<>();

  /**
   * The UI this controller controls
   */
  private final Ui ui;

  /**
   * constructor
   *
   * @param ui the UI this controller controls
   */
  public UiController(Ui ui) {
    this.ui = ui;
  }

  /**
   * Get the size of the board
   *
   * @return A Coord where the X is the width, and Y is the height
   */
  public Coord getBoardSize() {
    try {
      Coord size = this.ui.getBoardSize("""
          Hello! Welcome to the OOD BattleSalvo Game!
          Please enter a valid height and width below:
          ------------------------------------------------------
          """);
      if (this.validateSize(size)) {
        return size;
      }
      throw new IOException();
    } catch (IOException e) {
      while (true) {
        try {
          Coord size = this.ui.getBoardSize("""
              Uh Oh! You've entered invalid dimensions. Please remember that the height and width
              of the game must be in the range [6, 15], inclusive. Try again!
              ------------------------------------------------------
              """);
          if (this.validateSize(size)) {
            return size;
          }
          throw new IOException();
        } catch (IOException err) {
          // keep trying to get a new output until a valid one
        }
      }
    }
  }

  /**
   * Confirm that the board size if valid
   *
   * @param size the size
   * @return if it is valid
   */
  private boolean validateSize(Coord size) {
    return !size.outOfBound(new Coord(6, 6), new Coord(16, 16));
  }

  /**
   * Get the numbers of each ship type
   *
   * @param count The total number of ships
   * @return A map of the ShipType to the number of that ship
   */
  public Map<ShipType, Integer> shipCounts(int count) {
    while (true) {
      try {
        Map<ShipType, Integer> map = this.ui.shipCounts("""
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size\s""" + count + """
            .
            --------------------------------------------------------------------------------
            """);
        if (this.validateShips(map, count)) {
          return map;
        }
        throw new IOException();
      } catch (IOException e) {
        while (true) {
          try {
            Map<ShipType, Integer> map = this.ui.shipCounts(
                "Uh Oh! You've entered invalid fleet sizes.\n" + """
                Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
                Remember, your fleet may not exceed size\s""" + count + """
                .
                --------------------------------------------------------------------------------
                """);
            if (this.validateShips(map, count)) {
              return map;
            }
            throw new IOException();
          } catch (IOException err) {
            // keep trying to get a new output until a valid one
          }
        }
      }
    }
  }

  /**
   * Confirms if a ship map is valid
   *
   * @param map the ship map
   * @param count the number of total ships
   * @return if the map is valid
   */
  private boolean validateShips(Map<ShipType, Integer> map, int count) {
    return map.values().stream().reduce(0, Integer::sum) == count
        && map.values().stream().noneMatch((x) -> x == 0);
  }

  /**
   * Displays a board object
   *
   * @param b The board to display
   * @param desc A title for this board
   */
  public void displayBoard(DisplayableBoard b, String desc) {
    this.ui.displayBoard(b, desc);
  }

  /**
   * Gets the shots this user will fire
   *
   * @param n The number of shots
   * @param height The height of the board
   * @param width The width of the board
   * @return The list of coordinates the player chose to fire at
   * @throws IOException Failing to print output to the output stream
   */
  public ArrayList<Coord> getShots(int n, int width, int height) throws IOException {
    try {
      ArrayList<Coord> shots = this.ui.getShots(n, "Please enter " + n + " shots:\n"
          + "------------------------------------------------------------------\n");
      if (this.validateShots(shots, n, width, height)) {
        return shots;
      }
      throw new IOException();
    } catch (IOException e) {
      while (true) {
        try {
          ArrayList<Coord> shots = this.ui.getShots(n,
              "Invalid shots, please try again :3\n"
              + "Please enter " + n + " shots:\n"
              + "------------------------------------------------------------------\n");
          if (this.validateShots(shots, n, width, height)) {
            return shots;
          }
          throw new IOException();
        } catch (IOException err) {
          // keep trying to get a new output until a valid one
        }
      }
    }
  }

  /**
   * determine if a volley is valid
   *
   * @param shots the shots in the volley
   * @param n the number of shots that should be in the volley
   * @param width the width of the board
   * @param height the height of the board
   * @return if a volley is valid
   */
  private boolean validateShots(ArrayList<Coord> shots, int n, int width, int height) {
    boolean result = shots.size() == n && shots.stream()
        .noneMatch((s) ->
            s.outOfBound(new Coord(0, 0), new Coord(width, height))
         || this.guesses.contains(s))
        && allAreUnique(shots);
    if (result) {
      this.guesses.addAll(shots);
    }
    return result;
  }

  /**
   * check if all values are unique in a list
   *
   * @param list the list
   * @return if all elements are unique
   * @param <T> the type of elements in this list
   */
  private <T> boolean allAreUnique(ArrayList<T> list) {
    for (T item : list) {
      if (list.stream().filter((element) -> element.equals(item))
          .collect(Collectors.toCollection(ArrayList::new)).size() != 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * displays the ending message
   *
   * @param msg the message to display
   */
  public void displayEndMessage(String msg) {
    this.ui.displayEndMessage(msg);
  }
}
