package cs3500.pa03.model;

import cs3500.pa03.controller.UiController;
import java.io.IOException;
import java.util.List;

/**
 * Represents a real life human who is playing the game through some Ui
 */
public class HumanPlayer extends AbstractPlayer {
  /**
   * The ui the human uses to control this player
   */
  private final UiController ui;

  /**
   * Default constructor for now
   *
   * @param board the board this player will start off with
   * @param ui the UI this player will use to interact with the player
   */
  public HumanPlayer(Board board, UiController ui) {
    super(board);
    this.ui = ui;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "A real life human (woah)";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    // Get the shots from the user input via UI
    try {
      this.ui.displayBoard(this.board, "\nYour board:");
      this.ui.displayBoard(this.shotBoard, "\nEnemy board:");
      List<Coord> shots = this.ui.getShots(
          Math.min(this.board.getShips().size(), this.shotBoard.remainingSpaces()),
          this.board.getWidth(), this.board.getHeight());
      this.shotBoard.addAllToMissed(shots);
      return shots;
    } catch (IOException e) {
      // todo remove this
      throw new RuntimeException("For some reason failed to print output");
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
