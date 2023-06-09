package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.UiController;
import cs3500.pa03.view.Cli;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HumanPlayerTest {

  UiController ui;
  Board board;

  Readable input1;

  @BeforeEach
  public void setup() {
    ArrayList<Coord> shipPos = new ArrayList<>();
    shipPos.add(new Coord(0, 0));
    shipPos.add(new Coord(1, 0));
    shipPos.add(new Coord(2, 0));
    Ship sub = new Ship(shipPos);
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(sub);
    this.board = new Board(3, 3, ships);

    this.input1 = new StringReader("0 0\n1 0\n3 3\n2 0");
    Appendable output1 = new StringBuilder();
    this.ui = new UiController(new Cli(input1, output1));
  }

  @Test
  public void testShooting() {
    /*
    HumanPlayer human = new HumanPlayer(board, ui);
    human.setup(3, 3, new HashMap<>());

    List<Coord> result = new ArrayList<>();

    result.add(new Coord(0, 0));
    assertEquals(human.takeShots(), result);

    result.clear();
    result.add(new Coord(1, 0));
    assertEquals(human.takeShots(), result);

    // Skips 3 3 because that it out of bounds
    result.clear();
    result.add(new Coord(2, 0));
    assertEquals(human.takeShots(), result);
    */
  }
}