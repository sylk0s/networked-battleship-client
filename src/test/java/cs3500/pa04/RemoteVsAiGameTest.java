package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.view.Cli;
import java.io.IOException;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

class RemoteVsAiGameTest {

  @Test
  void testConstruction() {
    assertThrows(IOException.class,
        () -> new RemoteVsAiGame("notahostname", 12345,
              new Cli(new StringReader(""), System.out)));
  }
}