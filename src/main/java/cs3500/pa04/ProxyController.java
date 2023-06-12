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
  private final Socket server;

  /**
   * The input stream from this socket
   */
  private final InputStream in;

  /**
   * The output stream to the socket
   */
  private final PrintStream out;

  /**
   * The mapper to map to and from json objects
   */
  private final ObjectMapper mapper;

  /**
   * The receiver which dispatches on the messages and determines
   */
  private final MessageReceiver recv;

  /**
   * Constructor for the proxy controller
   *
   * @param server The socket this controller should connect to
   * @param player The local player
   * @throws IOException for failing to get IO streams
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.recv = new MessageReceiver(player);
    this.in = this.server.getInputStream();
    this.out = new PrintStream(this.server.getOutputStream());
    this.mapper = new ObjectMapper();
  }

  /**
   * Runs the proxy controller
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);

        //System.out.println(message);

        JsonNode response = this.recv.receiveMessage(message);
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
  private void sendMessage(JsonNode message) {
    this.out.println(message);
  }
}
