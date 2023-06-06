package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordTest {

  @Test
  public void testEquality() {
    Coord a = new Coord(0, 0);
    assertEquals(a, a);

    Coord b = new Coord(1, 0);
    assertNotEquals(a, b);

    Coord c = new Coord(0, 1);
    assertNotEquals(b, c);

    Coord d = new Coord(-1, 0);
    assertNotEquals(b, d);

    Coord e = new Coord(2, 3);
    assertEquals(e, e);

    Coord f = new Coord(2, 3);
    assertEquals(e, f);

    int aaa = 0;
    assertNotEquals(f, aaa);
  }

  @Test
  public void testBounds() {
    Coord min = new Coord(0, 0);
    Coord max = new Coord(5, 5);
    Coord in = new Coord(2, 2);

    assertFalse(in.outOfBound(min, max));
    assertTrue(in.outOfBound(max, min));

    Coord bottom = new Coord(0, 3);
    Coord top = new Coord(3, 5);

    assertFalse(bottom.outOfBound(min, max));
    assertTrue(top.outOfBound(min, max));
  }
}