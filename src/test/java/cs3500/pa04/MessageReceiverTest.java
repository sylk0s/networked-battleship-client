package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.StupidAiPlayer;
import cs3500.pa04.json.EndGameRequestMessage;
import cs3500.pa04.json.Fleet;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinMessage;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestMessage;
import cs3500.pa04.json.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageReceiverTest {
  private MessageReceiver recv;

  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    Player p = new StupidAiPlayer();
    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 1);
    map1.put(ShipType.BATTLESHIP, 2);
    map1.put(ShipType.DESTROYER, 2);
    map1.put(ShipType.SUBMARINE, 1);
    p.setup(6, 7, map1);
    this.recv = new MessageReceiver(p);
    this.mapper = new ObjectMapper();
  }

  @Test
  void receiveJoin() {
    MessageJson request = new MessageJson("join",
        new ObjectMapper().getNodeFactory().missingNode());

    JoinMessage joinArgs = new JoinMessage("aaa", GameType.SINGLE);
    JsonNode args = this.serializeRecord(joinArgs);
    JsonNode response = this.serializeRecord(new MessageJson("join", args));

    try {
      assertEquals(this.recv.receiveMessage(request), response);
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  void receiveSetup() {
    Map<ShipType, Integer> map1 = new HashMap<>();
    map1.put(ShipType.CARRIER, 1);
    map1.put(ShipType.BATTLESHIP, 2);
    map1.put(ShipType.DESTROYER, 2);
    map1.put(ShipType.SUBMARINE, 1);
    MessageJson request = new MessageJson("setup",
        this.serializeRecord(new SetupRequestMessage(6, 7, map1)));

    try {
      assertEquals(mapper.convertValue(
          mapper.convertValue(this.recv.receiveMessage(request), MessageJson.class).arguments(),
          Fleet.class).fleet().size(), 6);
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  void receiveTakeShots() {
    MessageJson request = new MessageJson("take-shots",
        new ObjectMapper().getNodeFactory().missingNode());

    try {
      assertEquals(mapper.convertValue(
          mapper.convertValue(this.recv.receiveMessage(request), MessageJson.class).arguments(),
          Volley.class).coordinates().size(), 6);
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  void receiveSuccessful() {
    // todo test side effects?
    MessageJson request = new MessageJson("successful-hits",
        this.serializeRecord(new Volley(new ArrayList<>())));

    try {
      assertEquals(this.serializeRecord(new MessageJson("successful-hits",
              new ObjectMapper().getNodeFactory().missingNode())),
          this.recv.receiveMessage(request));
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  void receiveReport() {
    MessageJson request = new MessageJson("report-damage",
        this.serializeRecord(new Volley(new ArrayList<>())));

    try {
      assertEquals(this.serializeRecord(request), this.recv.receiveMessage(request));
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  void receiveEnd() {
    EndGameRequestMessage args = new EndGameRequestMessage(GameResult.WIN, "some reason");
    MessageJson request = new MessageJson("end-game", this.serializeRecord(args));

    MessageJson response = new MessageJson("end-game",
        new ObjectMapper().getNodeFactory().missingNode());

    try {
      assertEquals(this.recv.receiveMessage(request), this.serializeRecord(response));
    } catch (JsonProcessingException e) {
      fail();
    }
  }

//  @Test
//  void throwsUnserializable() {
//    assertThrows(
//        () -> this.recv.receiveMessage(new NotJson("aaa"));
//  }

  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  private JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    try {
      return new ObjectMapper().convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }
}