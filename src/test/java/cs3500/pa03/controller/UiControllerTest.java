package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.CellType;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Cli;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UiControllerTest {
  Appendable output;

  @BeforeEach
  public void setup() {
    this.output = new StringBuilder();
  }

  @Test
  public void testGetBoardSize() {
    // Valid inputs
    Readable input1 = new StringReader("6 6");
    assertEquals(new UiController(new Cli(input1, this.output)).getBoardSize(),
        new Coord(6, 6));

    Readable input2 = new StringReader("7 8");
    assertEquals(new UiController(new Cli(input2, this.output)).getBoardSize(),
        new Coord(8, 7));

    Readable input3 = new StringReader("10 10");
    assertEquals(new UiController(new Cli(input3, this.output)).getBoardSize(),
        new Coord(10, 10));

    Readable input4 = new StringReader("16 6\n21 4\n6 6");
    assertEquals(new UiController(new Cli(input4, this.output)).getBoardSize(),
        new Coord(6, 6));

    Readable input5 = new StringReader("6 5\n6 6");
    assertEquals(new UiController(new Cli(input5, this.output)).getBoardSize(),
        new Coord(6, 6));

    Readable input6 = new StringReader("aaa\n6 6");
    assertEquals(new UiController(new Cli(input6, this.output)).getBoardSize(),
        new Coord(6, 6));
  }

  @Test
  public void testGetShipCounts() {
    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 3);
    map1.put(ShipType.BATTLESHIP, 1);
    map1.put(ShipType.DESTROYER, 1);
    map1.put(ShipType.SUBMARINE, 1);
    Readable input1 = new StringReader("3 1 1 1");
    assertEquals(new UiController(new Cli(input1, this.output)).shipCounts(6),
        map1);

    Readable input2 = new StringReader("3 2 1 0\n0 0 0 42\n3 1 1 1");
    assertEquals(new UiController(new Cli(input2, this.output)).shipCounts(6),
        map1);

    Map<ShipType, Integer> map3 = new HashMap<>();
    map3.put(ShipType.CARRIER, 1);
    map3.put(ShipType.BATTLESHIP, 2);
    map3.put(ShipType.DESTROYER, 2);
    map3.put(ShipType.SUBMARINE, 1);
    Readable input3 = new StringReader("3 34 2 8\n1 2 2 1");
    assertEquals(new UiController(new Cli(input3, this.output)).shipCounts(6),
        map3);

    Map<ShipType, Integer> map4 = new HashMap<>();
    map4.put(ShipType.CARRIER, 2);
    map4.put(ShipType.BATTLESHIP, 1);
    map4.put(ShipType.DESTROYER, 1);
    map4.put(ShipType.SUBMARINE, 2);
    Readable input4 = new StringReader("a 4 2 n 2\n2 1 1 2");
    assertEquals(new UiController(new Cli(input4, this.output)).shipCounts(6),
        map4);
  }

  @Test
  public void testDisplayBoard() {
    // Setup manually for a vertical submarine on a 3x3 board
    ArrayList<Coord> ship1 = new ArrayList<>();
    ship1.add(new Coord(0, 0));
    ship1.add(new Coord(0, 1));
    ship1.add(new Coord(0, 2));
    Ship sub = new Ship(ship1);
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(sub);
    Board board = new Board(3, 3, ships);

    UiController controller = new UiController(
        new Cli(new InputStreamReader(System.in), this.output));

    String result = "Some board:\n"
        + CellType.SUBMARINE + " " + CellType.EMPTY + " " + CellType.EMPTY + " \n"
        + CellType.SUBMARINE + " " + CellType.EMPTY + " " + CellType.EMPTY + " \n"
        + CellType.SUBMARINE + " " + CellType.EMPTY + " " + CellType.EMPTY + " \n";

    controller.displayBoard(board, "Some board:");
    assertEquals(this.output.toString(), result);
  }

  @Test
  public void testGetShots() {
    int n = 3;

    Readable input1 = new StringReader("3 0\n1 2\n3 3\n");
    ArrayList<Coord> result1 = new ArrayList<>();
    result1.add(new Coord(3, 0));
    result1.add(new Coord(1, 2));
    result1.add(new Coord(3, 3));
    try {
      assertEquals(new UiController(new Cli(input1, this.output)).getShots(n, 6, 6),
          result1);
      //assertEquals(this.output.toString(), "input");
    } catch (IOException e) {
      fail();
    }

    // This tests a ton of cases that will fail and asserts that only the final one works
    Readable input2 = new StringReader("3 3\n1 2\n3 3\n" + "3 3\n3 3\n2 3\n" + "0 -1\n0 0\n1 2"
        + "0 a\n" + "9 4\n0 0\n3 4\n" + "0 1\n0 2\n3 3\n");
    ArrayList<Coord> result2 = new ArrayList<>();
    result2.add(new Coord(0, 1));
    result2.add(new Coord(0, 2));
    result2.add(new Coord(3, 3));

    try {
      ArrayList<Coord> shots = new UiController(new Cli(input2, this.output))
          .getShots(n, 7, 6);
      assertEquals(shots, result2);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testEndMessage() {
    Readable input = new StringReader("aaa");
    Appendable out = new StringBuilder();
    UiController cli = new UiController(new Cli(input, out));
    cli.displayEndMessage("end message");
    assertEquals(out.toString(), "end message");
  }
}