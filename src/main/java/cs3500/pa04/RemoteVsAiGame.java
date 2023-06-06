package cs3500.pa04;

import cs3500.pa03.controller.Game;
import cs3500.pa03.model.Player;
import java.io.IOException;
import java.net.Socket;

public class RemoteVsAiGame implements Game {

  ProxyController controller;

  Player player;

  RemoteVsAiGame() {
    try {
      this.controller = new ProxyController(new Socket("127.0.0.1", 35001), this.player);
    } catch (IOException e) {
      System.err.println("Failed to connect to the port");
    }
  }

  @Override
  public void run() {
    //todo
  }
}