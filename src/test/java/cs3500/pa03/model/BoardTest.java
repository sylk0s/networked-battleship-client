package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  public void testShootShots() {
    ArrayList<Coord> shipPos = new ArrayList<>();
    shipPos.add(new Coord(0, 0));
    shipPos.add(new Coord(1, 0));
    shipPos.add(new Coord(2, 0));
    Ship sub = new Ship(shipPos);
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(sub);
    Board board = new Board(3, 3, ships);

    board.shootAt(new Coord(0, 0));
    board.updateShips();
    board.shootAt(new Coord(1, 0));
    board.updateShips();
    board.shootAt(new Coord(2, 2));
    board.updateShips();

    assertEquals(board.getShips().size(), 1);

    board.shootAt(new Coord(2, 0));
    board.updateShips();

    assertEquals(board.getShips().size(), 0);

  }

  @Test
  public void testRandomPlacement() {
    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.SUBMARINE, 1);

    try {
      Board board1 = new Board(3, 3, map1);
      assertEquals(board1.getShips().size(), 1);
    } catch (IllegalStateException e) {
      fail();
    }

    Map<ShipType, Integer> map2 = new HashMap<>();
    map2.put(ShipType.SUBMARINE, 3);

    try {
      Board board2 = new Board(3, 3, map2);
      assertEquals(board2.getShips().size(), 3);
    } catch (IllegalStateException e) {
      fail();
    }

    Map<ShipType, Integer> map3 = new HashMap<>();

    try {
      Board board3 = new Board(3, 3, map3);
      assertEquals(board3.getShips().size(), 0);
    } catch (IllegalStateException e) {
      fail();
    }

    Map<ShipType, Integer> map4 = new HashMap<>();
    map4.put(ShipType.SUBMARINE, 4);
    Map<ShipType, Integer> map5 = new HashMap<>();
    map5.put(ShipType.DESTROYER, 1);

    assertThrows(IllegalStateException.class, () -> new Board(3, 3, map4));
    assertThrows(IllegalStateException.class, () -> new Board(3, 3, map5));

    try {
      Board board4 = new Board(4, 4, map5);
      assertEquals(board4.getShips().size(), 1);
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void testGetCoords() {
    ArrayList<Coord> coords = new ArrayList<>();
    coords.add(new Coord(0, 0));
    coords.add(new Coord(1, 0));
    coords.add(new Coord(2, 0));
    coords.add(new Coord(0, 1));
    coords.add(new Coord(1, 1));
    coords.add(new Coord(2, 1));
    coords.add(new Coord(0, 2));
    coords.add(new Coord(1, 2));
    coords.add(new Coord(2, 2));

    Board board = new Board(3, 3, new ArrayList<>());

    assertTrue(listSetEquality(board.coordsOnBoard(), coords));
  }

  private <T> boolean listSetEquality(ArrayList<T> list1, ArrayList<T> list2) {
    for (T item : list1) {
      if (list2.stream().filter((element) -> element.equals(item))
          .collect(Collectors.toCollection(ArrayList::new)).size() != 1) {
        return false;
      }
    }
    return true;
  }

  @Test
  public void testCellTypes() {
    ArrayList<Coord> shipPos = new ArrayList<>();
    shipPos.add(new Coord(0, 0));
    shipPos.add(new Coord(1, 0));
    shipPos.add(new Coord(2, 0));
    Ship sub = new Ship(shipPos);
    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(sub);
    Board board = new Board(3, 3, ships);

    board.shootAt(new Coord(1, 0));
    board.shootAt(new Coord(1, 1));

    CellType[][] cells = new CellType[3][3];
    cells[0][0] = CellType.SUBMARINE;
    cells[1][0] = CellType.HIT;
    cells[2][0] = CellType.SUBMARINE;
    cells[0][1] = CellType.EMPTY;
    cells[1][1] = CellType.MISS;
    cells[2][1] = CellType.EMPTY;
    cells[0][2] = CellType.EMPTY;
    cells[1][2] = CellType.EMPTY;
    cells[2][2] = CellType.EMPTY;

    for (int i = 0; i < 3; i += 1) {
      for (int j = 0; j < 3; j += 1) {
        assertEquals(cells[i][j], board.coordTypes()[i][j]);
      }
    }
  }
}