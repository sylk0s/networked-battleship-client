package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.StupidAiPlayer;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

  @Test
  void testConstructions() {
    // test proper

    // Confirms that it throws the exception if it's wrong
    assertThrows(IOException.class, () -> new ProxyController(new Mocket(), new StupidAiPlayer()));
  }

}