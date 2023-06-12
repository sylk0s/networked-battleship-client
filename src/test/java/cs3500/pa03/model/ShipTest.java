package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.json.Orientation;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  private ArrayList<Coord> coords;
  private Ship ship;

  @BeforeEach
  public void setup() {
    this.coords = new ArrayList<>();
    this.coords.add(new Coord(1, 2));
    this.coords.add(new Coord(1, 3));
    this.coords.add(new Coord(1, 4));
    ship = new Ship(this.coords);
  }

  @Test
  public void invalidSize() {
    ArrayList<Coord> coords1 = new ArrayList<>();
    coords1.add(new Coord(1, 2));
    coords1.add(new Coord(1, 3));

    Ship ship1 = new Ship(coords1);

    assertThrows(IllegalStateException.class, ship1::type);
  }

  @Test
  public void testShooting() {
    this.ship.fireAt(new Coord(1, 2));
    this.ship.fireAt(new Coord(1, 3));
    assertFalse(this.ship.hadBeenSunk());
    this.ship.fireAt(new Coord(2, 4));
    assertFalse(this.ship.hadBeenSunk());
    this.ship.fireAt(new Coord(1, 4));
    assertTrue(this.ship.hadBeenSunk());
  }

  @Test
  public void testExistsAt() {
    assertTrue(this.ship.shipExistsAt(new Coord(1, 2)));
    assertTrue(this.ship.shipExistsAt(new Coord(1, 3)));
    assertFalse(this.ship.shipExistsAt(new Coord(0, 2)));
  }

  @Test
  public void testType() {
    assertEquals(this.ship.type(), ShipType.SUBMARINE);
    this.coords.add(new Coord(1, 5));
    assertEquals(this.ship.type(), ShipType.DESTROYER);
    this.coords.add(new Coord(1, 6));
    assertEquals(this.ship.type(), ShipType.BATTLESHIP);
    this.coords.add(new Coord(1, 7));
    assertEquals(this.ship.type(), ShipType.CARRIER);
    this.coords.add(new Coord(1, 8));
  }

  @Test
  public void testJsonThings() {
    ArrayList<Coord> coords1 = new ArrayList<>();
    coords1.add(new Coord(0, 0));
    coords1.add(new Coord(1, 0));
    Ship ship2 = new Ship(coords1);

    assertEquals(ship2.direction(), Orientation.HORIZONTAL);
    assertEquals(ship2.leastCoord(), new Coord(0,0));

    ArrayList<Coord> coords2 = new ArrayList<>();
    coords2.add(new Coord(0, 0));
    coords2.add(new Coord(0, 1));
    Ship ship3 = new Ship(coords2);

    assertEquals(ship3.direction(), Orientation.VERTICAL);
    assertEquals(ship3.leastCoord(), new Coord(0,0));

    ArrayList<Coord> coords3 = new ArrayList<>();
    coords3.add(new Coord(0, 0));
    coords3.add(new Coord(1, 1));
    Ship ship4 = new Ship(coords3);

    assertThrows(IllegalStateException.class, ship4::direction);

    assertEquals(new Ship(2, Orientation.VERTICAL, new Coord(0,0)),
        ship3);

    assertNotEquals(ship, 0);
  }
}