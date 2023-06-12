package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Cli;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SmarterAiPlayerTest {
  Map<ShipType, Integer> map1;
  Player player1;

  @BeforeEach
  void setup() {
    map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 1);
    map1.put(ShipType.BATTLESHIP, 2);
    map1.put(ShipType.DESTROYER, 2);
    map1.put(ShipType.SUBMARINE, 1);
    player1 = new SmarterAiPlayer();
    player1.setup(6, 6, map1);
  }

  @Test
  public void testTakeShots() {
    List<Coord> shots1 = player1.takeShots();

    // Took the right amount of shots
    assertEquals(shots1.size(), 6);

    // All shots in bounds
    for (Coord shot : shots1) {
      assertFalse(shot.outOfBound(new Coord(0, 0), new Coord(6, 6)));
    }

    Map<ShipType, Integer> map2 = new HashMap<>();
    map2.put(ShipType.CARRIER, 1);
    map2.put(ShipType.BATTLESHIP, 3);
    map2.put(ShipType.DESTROYER, 2);
    map2.put(ShipType.SUBMARINE, 1);
    Player player2 = new SmarterAiPlayer();
    player2.setup(7, 8, map2);
    List<Coord> shots2 = player2.takeShots();

    // Took the right amount of shots
    assertEquals(shots2.size(), 7);

    // All shots in bounds
    for (Coord shot : shots2) {
      assertFalse(shot.outOfBound(new Coord(0, 0), new Coord(7, 8)));
    }
  }

  @Test
  void testMarkingShots() {
    List<Coord> shots1 = player1.takeShots();

    // simulate no shots hitting
    player1.successfulHits(new ArrayList<>());
    List<Coord> shots2 = player1.takeShots();

    for (Coord c : shots2) {
      assertFalse(shots1.contains(c));
    }

    shots1.addAll(shots2);

    List<Coord> shots3 = player1.takeShots();
    // simulate all shots hitting
    player1.successfulHits(shots3);

    for (Coord c : shots3) {
      assertFalse(shots1.contains(c));
    }
  }

  @Test
  void testToStringAndDisplay() {
    // shots are deterministic for the first run since they pick the most probable locations
    assertEquals(this.player1.takeShots().toString(),
        "[{ 2, 2 }, { 2, 3 }, { 3, 2 }, { 3, 3 }, { 1, 2 }, { 1, 3 }]");

    ProbabilityBoard board = new ProbabilityBoard(6, 6, map1);

    StringBuilder sb = new StringBuilder();
    Cli cli = new Cli(new StringReader(""), sb);
    cli.displayBoardNoFormat(board);
    assertEquals(sb.toString(), """
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        """);

    board.markAsMiss(new Coord(0, 0));
    board.markAsHit(new Coord(1, 1));

    StringBuilder sb2 = new StringBuilder();
    Cli cli2 = new Cli(new StringReader(""), sb2);
    cli2.displayBoardNoFormat(board);
    assertEquals(sb2.toString(), """
        - . . . . .\s
        . * . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        """);
  }
}