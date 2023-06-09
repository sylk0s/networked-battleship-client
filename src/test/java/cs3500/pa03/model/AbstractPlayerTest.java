package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPlayerTest {

  Board board;
  Player player;

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
    this.player = new StupidAiPlayer(this.board);
  }

  @Test
  public void testSetup() {
    /*
    // Opponent board has no spaces so wont take shots
    assertEquals(this.player.takeShots(), new ArrayList<>());

    // dummy map, not actually used...
    Map<ShipType, Integer> map = new HashMap<>();

    // Has one ship
    assertEquals(this.player.setup(3, 3, map).size(), 1);

    // Ship takes a shot
    assertEquals(this.player.takeShots().size(), 1);

    // Takes 8 more shots
    for (int i = 0; i < 8; i += 1) {
      this.player.takeShots();
    }

    // No spaces left on board, no more shots
    assertEquals(this.player.takeShots().size(), 0);

     */
  }

  @Test
  public void testReportDamage() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));

    ArrayList<Coord> result = new ArrayList<>();
    result.add(new Coord(0, 0));

    assertEquals(this.player.reportDamage(shots), result);

    ArrayList<Coord> shots2 = new ArrayList<>();
    shots.add(new Coord(0, 2));
    shots.add(new Coord(0, 1));

    assertEquals(this.player.reportDamage(shots2), new ArrayList<>());
  }
}