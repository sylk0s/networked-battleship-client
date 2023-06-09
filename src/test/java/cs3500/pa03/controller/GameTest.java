package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.Cli;
import cs3500.pa03.view.Ui;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

class GameTest {
  /*
  @Test
  public void testRun1() {
    Readable input = new StringReader(
        "6 6\n"
            + "3 1 1 1\n"
            + "0 0\n1 1\n2 2\n3 3\n4 4\n5 5\n"
            + "0 5\n1 4\n2 3\n3 2\n4 1\n5 0\n"
            + "0 2\n0 3\n3 0\n2 0\n2 5\n3 5\n"
            + "2 1\n3 1\n2 4\n3 4\n0 1\n1 0\n"
            + "4 0\n5 1\n0 4\n1 2\n1 3\n4 2\n"
            + "1 5\n4 5\n4 3\n5 3\n5 4\n");
    Appendable output = new StringBuilder();
    Ui ui = new Cli(input, output);

    Game game = new HumanVsAiGame(ui, new Random(42));
    game.run();

    String result = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        --------------------------------------------------------------------------------
        
        Your board:
        S S S . . .\s
        C C C C C C\s
        C C C C C C\s
        C C C C C C\s
        D D D D . .\s
        B B B B B .\s
        
        Enemy board:
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        Please enter 6 shots:
        ------------------------------------------------------------------
        
        Your board:
        S S S . - .\s
        C C C * C C\s
        C C C C C C\s
        C * C C C C\s
        D D D D - .\s
        B B * * B .\s
        
        Enemy board:
        * . . . . .\s
        . * . . . .\s
        . . * . . .\s
        . . . * . .\s
        . . . . - .\s
        . . . . . -\s
        Please enter 6 shots:
        ------------------------------------------------------------------
        
        Your board:
        S S S . - -\s
        * * * * C C\s
        C * C C C C\s
        C * C C C C\s
        D D D D - .\s
        B * * * B .\s
        
        Enemy board:
        * . . . . *\s
        . * . . * .\s
        . . * * . .\s
        . . * * . .\s
        . * . . - .\s
        - . . . . -\s
        Please enter 6 shots:
        ------------------------------------------------------------------
        
        Your board:
        S * S - - -\s
        * * * * C *\s
        C * C C C C\s
        C * C C C C\s
        * D D D - -\s
        * * * * B .\s
        
        Enemy board:
        * . * * . *\s
        . * . . * .\s
        * . * * . .\s
        * . * * . .\s
        . * . . - .\s
        - . * * . -\s
        Please enter 6 shots:
        ------------------------------------------------------------------
        
        Your board:
        S * S - - -\s
        * * * * * *\s
        C * * C C C\s
        C * C C C C\s
        * * D * - -\s
        * * * * * -\s
        
        Enemy board:
        * * * * . *\s
        * * * * * .\s
        * . * * . .\s
        * . * * . .\s
        . * * - - .\s
        - . * * . -\s
        Please enter 4 shots:
        ------------------------------------------------------------------
        
        Your board:
        * * S - - -\s
        * * * * * *\s
        C * * * C *\s
        * * C C * C\s
        * * * * - -\s
        * * * * * -\s
        
        Enemy board:
        * * * * * *\s
        * * * * * *\s
        * * * * . .\s
        * . * * . .\s
        * * * - - .\s
        - . * * . -\s
        Please enter 3 shots:
        ------------------------------------------------------------------
        
        Your board:
        * * * - - -\s
        * * * * * *\s
        * * * * C *\s
        * * * C * C\s
        * * * * - -\s
        * * * * * -\s
        
        Enemy board:
        * * * * * *\s
        * * * * * *\s
        * * * * * .\s
        * * * * . .\s
        * * * - - .\s
        - * * * . -\s
        Please enter 2 shots:
        ------------------------------------------------------------------
        
        Your board:
        * * * - - -\s
        * * * * * *\s
        * * * * C *\s
        * * * * * *\s
        * * * * - -\s
        * * * * * -\s
        
        Enemy board:
        * * * * * *\s
        * * * * * *\s
        * * * * * .\s
        * * * * * .\s
        * * * - - .\s
        - * * * * -\s
        Please enter 1 shots:
        ------------------------------------------------------------------
        The human and the AI tied!""";

    assertEquals(result, output.toString().replaceAll("\u001B\\[[;\\d]*m", ""));
  }

  @Test
  public void testRun2() {
    Readable input = new StringReader(
        "7 6\n"
            + "4 1 1 1\n"
            + "3 1 1 1\n"
            + "0 0\n0 0\n0 0\n0 0\n0 0\n0 0\n"
            + "0 0\n1 1\n2 2\n3 3\n4 4\n5 5\n"
            + "0 5\n1 4\n2 3\n3 2\n4 1\n5 0\n"
            + "0 3\n2 0\n0 6\n1 6\n2 6\n3 6\n"
            + "4 6\n5 6\n1 0\n3 0\n4 0\n2 4\n"
            + "1 5\n2 5\n3 5\n4 5\n0 1\n"
            + "2 1\n3 1\n5 1\n0 4\n"
            + "3 4\n5 4\n0 2\n"
            + "1 3\n1 2\n4 2\n"
            + "4 3\n5 3\n5 2\n"
    );
    Appendable output = new StringBuilder();
    Ui ui = new Cli(input, output);

    Game game = new HumanVsAiGame(ui, new Random(42));
    game.run();

    String result = """
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        --------------------------------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        --------------------------------------------------------------------------------
                
        Your board:
        . C C . . .\s
        D C C C S B\s
        D C C C S B\s
        D C C C S B\s
        D C C C . B\s
        . C C C . B\s
        . . . C . .\s
                
        Enemy board:
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        . . . . . .\s
        Please enter 6 shots:
        ------------------------------------------------------------------
        Invalid shots, please try again :3
        Please enter 6 shots:
        ------------------------------------------------------------------
                
        Your board:
        . C C . . .\s
        D C C C S B\s
        D C * C S B\s
        D C C * S *\s
        D * C C . B\s
        . C C * . *\s
        . . . C . .\s
                
        Enemy board:
        - . . . . .\s
        . * . . . .\s
        . . * . . .\s
        . . . - . .\s
        . . . . * .\s
        . . . . . *\s
        . . . . . .\s
        Please enter 6 shots:
        ------------------------------------------------------------------
                
        Your board:
        - C C . . .\s
        D C C * S B\s
        D C * C S *\s
        * * C * S *\s
        D * C C . B\s
        . C C * . *\s
        . . . * . .\s
                
        Enemy board:
        - . . . . -\s
        . * . . * .\s
        . . * * . .\s
        . . - - . .\s
        . * . . * .\s
        * . . . . *\s
        . . . . . .\s
        Please enter 6 shots:
        ------------------------------------------------------------------
                
        Your board:
        - C C . . .\s
        D C C * * B\s
        * C * C S *\s
        * * * * S *\s
        D * C C - *\s
        . C C * . *\s
        . . . * . -\s
                
        Enemy board:
        - . * . . -\s
        . * . . * .\s
        . . * * . .\s
        - . - - . .\s
        . * . . * .\s
        * . . . . *\s
        - - - * . .\s
        Please enter 6 shots:
        ------------------------------------------------------------------
                
        Your board:
        - * C . . .\s
        D * * * * *\s
        * C * C * *\s
        * * * * S *\s
        D * C C - *\s
        . * C * . *\s
        . . . * . -\s
                
        Enemy board:
        - * * * * -\s
        . * . . * .\s
        . . * * . .\s
        - . - - . .\s
        . * * . * .\s
        * . . . . *\s
        - - - * * *\s
        Please enter 5 shots:
        ------------------------------------------------------------------
                
        Your board:
        - * C . . .\s
        D * * * * *\s
        * * * C * *\s
        * * * * S *\s
        D * * C - *\s
        - * C * - *\s
        . . . * . -\s
                
        Enemy board:
        - * * * * -\s
        * * . . * .\s
        . . * * . .\s
        - . - - . .\s
        . * * . * .\s
        * * * * * *\s
        - - - * * *\s
        Please enter 4 shots:
        ------------------------------------------------------------------
                
        Your board:
        - * C . - .\s
        D * * * * *\s
        * * * C * *\s
        * * * * * *\s
        D * * C - *\s
        - * C * - *\s
        . - . * . -\s
                
        Enemy board:
        - * * * * -\s
        * * * * * -\s
        . . * * . .\s
        - . - - . .\s
        * * * . * .\s
        * * * * * *\s
        - - - * * *\s
        Please enter 3 shots:
        ------------------------------------------------------------------
                
        Your board:
        - * C - - .\s
        * * * * * *\s
        * * * C * *\s
        * * * * * *\s
        D * * C - *\s
        - * C * - *\s
        . - . * . -\s
                
        Enemy board:
        - * * * * -\s
        * * * * * -\s
        * . * * . .\s
        - . - - . .\s
        * * * * * *\s
        * * * * * *\s
        - - - * * *\s
        Please enter 3 shots:
        ------------------------------------------------------------------
                
        Your board:
        - * C - - .\s
        * * * * * *\s
        * * * C * *\s
        * * * * * *\s
        D * * C - *\s
        - * C * - *\s
        - - . * . -\s
                
        Enemy board:
        - * * * * -\s
        * * * * * -\s
        * * * * * .\s
        - - - - . .\s
        * * * * * *\s
        * * * * * *\s
        - - - * * *\s
        Please enter 3 shots:
        ------------------------------------------------------------------
        The human won and the AI lost.""";

    assertEquals(result, output.toString().replaceAll("\u001B\\[[;\\d]*m", ""));
  }
  */
}