package cs3500.pa04;

import cs3500.pa03.controller.Game;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.Ui;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * A Game where an AI player plays against a remote player
 */
public class RemoteVsAiGame implements Game {

  /**
   * The controller for this game
   */
  ProxyController controller;

  /**
   * The local player in this game
   */
  Player player;

  /**
   * Constructor for the game
   *
   * @param hostname The hostname for this socket
   * @param port The port for this socket
   * @param ui The UI for this game
   * @param random The random seed for this game
   */
  RemoteVsAiGame(String hostname, int port, Ui ui, Random random) {
    try {
      Socket socket = new Socket(hostname, port);
      this.controller = new ProxyController(socket,
          new SmarterAiPlayer());
    } catch (IOException e) {
      System.err.println("Failed to connect to the port");
    }
  }

  /**
   * Runs this game
   */
  @Override
  public void run() {
    this.controller.run();
  }
}