package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StupidAiPlayerTest {

  @Test
  public void testTakeShots() {

    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 1);
    map1.put(ShipType.BATTLESHIP, 2);
    map1.put(ShipType.DESTROYER, 2);
    map1.put(ShipType.SUBMARINE, 1);
    Player player1 = new StupidAiPlayer(new Board(6, 6, map1));
    player1.setup(6, 6, map1);
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
    Player player2 = new StupidAiPlayer(new Board(7, 8, map2));
    player2.setup(7, 8, map2);
    List<Coord> shots2 = player2.takeShots();

    // Took the right amount of shots
    assertEquals(shots2.size(), 7);

    // All shots in bounds
    for (Coord shot : shots2) {
      assertFalse(shot.outOfBound(new Coord(0, 0), new Coord(7, 8)));
    }
  }
}