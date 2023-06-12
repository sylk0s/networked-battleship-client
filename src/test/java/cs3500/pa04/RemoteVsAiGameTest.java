package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cs3500.pa03.view.Cli;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

class RemoteVsAiGameTest {

  @Test
  void testConstruction() {
    assertDoesNotThrow(() -> {
      new RemoteVsAiGame("notahostname",
          12345,
          new Cli(new StringReader(""), System.out),
          new Random());
    });
  }
}