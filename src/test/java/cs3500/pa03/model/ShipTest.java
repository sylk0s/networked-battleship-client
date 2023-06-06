package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  ArrayList<Coord> coords;
  Ship ship;

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
}