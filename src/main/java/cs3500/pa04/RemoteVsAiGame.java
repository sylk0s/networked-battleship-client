package cs3500.pa04;

import cs3500.pa03.controller.Game;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.StupidAiPlayer;
import cs3500.pa03.view.Ui;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class RemoteVsAiGame implements Game {

  ProxyController controller;

  Player player;

  RemoteVsAiGame(String hostname, int port, Ui ui, Random random) {
    try {
      Socket socket = new Socket(hostname, port);
      this.controller = new ProxyController(socket,
          new StupidAiPlayer(new Board(0, 0, new ArrayList<>())));
    } catch (IOException e) {
      System.err.println("Failed to connect to the port");
    }
  }

  @Override
  public void run() {
    this.controller.run();
  }
}