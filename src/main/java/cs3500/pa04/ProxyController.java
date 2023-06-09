package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Player;
import cs3500.pa04.json.MessageJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * The proxy controller for the player
 */
public class ProxyController {

  /**
   * The socket this game is connected to
   */
  Socket server;

  /**
   * The input stream from this socket
   */
  InputStream in;

  /**
   * The output stream to the socket
   */
  PrintStream out;

  /**
   * The mapper to map to and from json objects
   */
  ObjectMapper mapper;

  /**
   * The receiver which dispatches on the messages and determines
   */
  MessageReceiver recv;

  /**
   * Constructor for the proxy controller
   *
   * @param server The socket this controller should connect to
   * @param player The local player
   */
  public ProxyController(Socket server, Player player) {
    this.server = server;
    this.recv = new MessageReceiver(player);
    try {
      this.in = this.server.getInputStream();
      this.out = new PrintStream(this.server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException("help");
    }
    this.mapper = new ObjectMapper();
  }

  /**
   * Runs the proxy controller
   */
  public void run() {
    try {
      System.out.println("in run");
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        System.out.println("awaiting next message...");
        MessageJson message = parser.readValueAs(MessageJson.class);
        System.out.println("recv msg: " + message.toString());
        JsonNode response = this.recv.receiveMessage(message);
        System.out.println("response: " + response.toString());
        this.sendMessage(response);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Sends the message on the server
   *
   * @param message The message to send
   */
  public void sendMessage(JsonNode message) {
    this.out.println(message);
  }
}
