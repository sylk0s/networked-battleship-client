package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CliTest {
  Appendable output;

  @BeforeEach
  public void setup() {
    this.output = new StringBuilder();
  }

  @Test
  public void testBoardSize() {
    // Valid inputs
    Readable input1 = new StringReader("6 6");
    Readable input2 = new StringReader("7 8");
    Readable input3 = new StringReader("10 10");

    try {
      assertEquals(new Cli(input1, this.output).getBoardSize("input"),
          new Coord(6, 6));
      assertEquals(this.output.toString(), "input");
    } catch (IOException e) {
      fail();
    }

    try {
      assertEquals(new Cli(input2, this.output).getBoardSize("input"),
          new Coord(8, 7));
    } catch (IOException e) {
      fail();
    }

    try {
      assertEquals(new Cli(input3, this.output).getBoardSize("input"),
          new Coord(10, 10));
    } catch (IOException e) {
      fail();
    }

    // Invalid numbers, these won't be caught by this method
    Readable input4 = new StringReader("11 6");
    Readable input5 = new StringReader("6 5");
    Readable input6 = new StringReader("0 0");

    try {
      assertEquals(new Cli(input4, this.output).getBoardSize("input"),
          new Coord(6, 11));
    } catch (IOException e) {
      fail();
    }

    try {
      assertEquals(new Cli(input5, this.output).getBoardSize("input"),
          new Coord(5, 6));
    } catch (IOException e) {
      fail();
    }

    try {
      assertEquals(new Cli(input6, this.output).getBoardSize("input"),
          new Coord(0, 0));
    } catch (IOException e) {
      fail();
    }

    // Random giberish, these will be caught
    Readable input7 = new StringReader("aaa");
    assertThrows(IOException.class, () -> new Cli(input7, this.output).getBoardSize("input"));

    Readable input8 = new StringReader(" ");
    assertThrows(IOException.class, () -> new Cli(input8, this.output).getBoardSize("input"));

    Readable input9 = new StringReader("a a");
    assertThrows(IOException.class, () -> new Cli(input9, this.output).getBoardSize("input"));

    Readable input10 = new StringReader("abcdef aldjfas 828");
    assertThrows(IOException.class, () -> new Cli(input10, this.output).getBoardSize("input"));

    Readable input11 = new StringReader("6 7 8 9");
    assertThrows(IOException.class, () -> new Cli(input11, this.output).getBoardSize("input"));

    Readable input12 = new StringReader(" 2 1 ah ");
    assertThrows(IOException.class, () -> new Cli(input12, this.output).getBoardSize("input"));
  }

  @Test
  public void testShipCounts() {
    Readable input1 = new StringReader("3 1 1 1");
    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 3);
    map1.put(ShipType.BATTLESHIP, 1);
    map1.put(ShipType.DESTROYER, 1);
    map1.put(ShipType.SUBMARINE, 1);
    try {
      assertEquals(new Cli(input1, this.output).shipCounts("input"),
          map1);
      assertEquals(this.output.toString(), "input");
    } catch (IOException e) {
      fail();
    }

    Readable input2 = new StringReader("3 2 1 0");
    Map<ShipType, Integer> map2 = new HashMap<>();
    map2.put(ShipType.CARRIER, 3);
    map2.put(ShipType.BATTLESHIP, 2);
    map2.put(ShipType.DESTROYER, 1);
    map2.put(ShipType.SUBMARINE, 0);
    try {
      assertEquals(new Cli(input2, this.output).shipCounts("input"),
          map2);
    } catch (IOException e) {
      fail();
    }

    Readable input3 = new StringReader("1 2 2 1");
    Map<ShipType, Integer> map3 = new HashMap<>();
    map3.put(ShipType.CARRIER, 1);
    map3.put(ShipType.BATTLESHIP, 2);
    map3.put(ShipType.DESTROYER, 2);
    map3.put(ShipType.SUBMARINE, 1);
    try {
      assertEquals(new Cli(input3, this.output).shipCounts("input"),
          map3);
    } catch (IOException e) {
      fail();
    }

    Readable input4 = new StringReader("3 34 2 8");
    Map<ShipType, Integer> map4 = new HashMap<>();
    map4.put(ShipType.CARRIER, 3);
    map4.put(ShipType.BATTLESHIP, 34);
    map4.put(ShipType.DESTROYER, 2);
    map4.put(ShipType.SUBMARINE, 8);
    try {
      assertEquals(new Cli(input4, this.output).shipCounts("input"),
          map4);
    } catch (IOException e) {
      fail();
    }

    Readable input5 = new StringReader("3 ");
    Readable input6 = new StringReader("a 4 2 n");
    Readable input7 = new StringReader("1 2 2 1 3");

    assertThrows(IOException.class, () -> new Cli(input5, output).shipCounts("input"));
    assertThrows(IOException.class, () -> new Cli(input6, output).shipCounts("input"));
    assertThrows(IOException.class, () -> new Cli(input7, output).shipCounts("input"));
  }

  // INTENTIONALLY ignores a redundant test for display board
  // because the test for UiController is basically just a call to this function
  // and so the tests would be exactly the same and wouldn't do anything extra

  @Test
  public void getShots() {
    int n = 3;

    Readable input1 = new StringReader("3 0\n1 2\n3 3\n");
    ArrayList<Coord> result1 = new ArrayList<>();
    result1.add(new Coord(3, 0));
    result1.add(new Coord(1, 2));
    result1.add(new Coord(3, 3));
    try {
      assertEquals(new Cli(input1, this.output).getShots(n, "input"),
          result1);
      assertEquals(this.output.toString(), "input");
    } catch (IOException e) {
      fail();
    }

    Readable input2 = new StringReader("3 3\n-1 2\n3 3\n");
    ArrayList<Coord> result2 = new ArrayList<>();
    result2.add(new Coord(3, 3));
    result2.add(new Coord(-1, 2));
    result2.add(new Coord(3, 3));
    try {
      assertEquals(new Cli(input2, this.output).getShots(n, "input"),
          result2);
    } catch (IOException e) {
      fail();
    }


    Readable input3 = new StringReader("3\n1 2\n3 3\n");
    Readable input4 = new StringReader("3 0\na 2\n3 3\n");
    Readable input5 = new StringReader("3 0\na 2");

    assertThrows(IOException.class, () -> new Cli(input3, this.output).getShots(n, "input"));
    assertThrows(IOException.class, () -> new Cli(input4, this.output).getShots(n, "input"));
    assertThrows(IOException.class, () -> new Cli(input5, this.output).getShots(n, "input"));
  }

  @Test
  public void testExceptions() {
    Readable input = new StringReader("aaa");
    assertDoesNotThrow(() -> new Cli(input, new MockOutput())
        .displayBoard(new Board(0, 0, new ArrayList<>()), ""));
    assertDoesNotThrow(() -> new Cli(input, new MockOutput())
        .displayBoardNoFormat(new Board(0, 0, new ArrayList<>())));
    assertDoesNotThrow(() -> new Cli(input, new MockOutput()).displayMessage(""));
  }
}