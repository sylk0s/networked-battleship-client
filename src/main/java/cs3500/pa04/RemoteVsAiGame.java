package cs3500.pa04;

import cs3500.pa03.controller.Game;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.Cli;
import cs3500.pa03.view.Ui;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * A Game where an AI player plays against a remote player
 */
public class RemoteVsAiGame implements Game {

  /**
   * The controller for this game
   */
  ProxyController controller;

  /**
   * The UI for this game
   */
  Ui ui;

  /**
   * Constructor for the game
   *
   * @param hostname The hostname for this socket
   * @param port The port for this socket
   * @param ui The UI for this game
   */
  RemoteVsAiGame(String hostname, int port, Ui ui) throws IOException {
    this.ui = ui;
    Socket socket = new Socket(hostname, port);
    this.controller = new ProxyController(socket,
        new SmarterAiPlayer(
            new Cli(new InputStreamReader(System.in), new PrintStream(System.out))));
  }

  /**
   * Runs this game
   */
  @Override
  public void run() {
    this.controller.run();
  }
}