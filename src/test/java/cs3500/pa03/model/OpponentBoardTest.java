package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.Cli;
import java.io.StringReader;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpponentBoardTest {
  OpponentBoard board;
  Appendable output;
  Readable input;
  Cli cli;

  @BeforeEach
  public void setup() {
    this.board = new OpponentBoard(3, 3);
    this.output = new StringBuilder();
    this.input = new StringReader("aaa");
    this.cli = new Cli(input, output);
  }

  @Test
  public void testAddingHits() {
    ArrayList<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(0, 1));
    this.board.addAllToMissed(coords);
    this.board.addHitShots(coords);

    this.cli.displayBoardNoFormat(this.board);
    assertEquals(this.output.toString(), "* . . \n* . . \n. . . \n");
  }

  @Test
  public void testMisses() {
    ArrayList<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    this.board.addAllToMissed(coords);

    this.cli.displayBoardNoFormat(this.board);
    assertEquals(this.output.toString(), "- - . \n. . . \n. . . \n");
  }

  @Test
  public void testInteractions() {
    ArrayList<Coord> hits = new ArrayList<>();
    hits.add(new Coord(0, 0));
    hits.add(new Coord(1, 0));

    ArrayList<Coord> all = new ArrayList<>();
    all.add(new Coord(0, 0));
    all.add(new Coord(1, 0));
    all.add(new Coord(0, 1));
    this.board.addAllToMissed(all);
    this.board.addHitShots(hits);

    this.cli.displayBoardNoFormat(this.board);
    assertEquals(this.output.toString(), "* * . \n- . . \n. . . \n");
  }
}