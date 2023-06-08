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

public class ProxyController {

  Socket server;

  InputStream in;
  PrintStream out;

  ObjectMapper mapper;

  MessageReceiver recv;

  public ProxyController(Socket server, Player player) {
    this.server = server;
    // TODO fix this!!!
    this.recv = new MessageReceiver(player);
    try {
      this.in = this.server.getInputStream();
      this.out = new PrintStream(this.server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException("help");
    }
    this.mapper = new ObjectMapper();
  }

  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);

        this.sendMessage(this.recv.receiveMessage(message));
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }



  public void sendMessage(JsonNode message) {
    this.out.append(message.asText()).append("\n");
  }
}
