package cs3500.pa04;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Player;
import cs3500.pa04.json.EndGameRequestMessage;
import cs3500.pa04.json.Fleet;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinMessage;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestMessage;
import cs3500.pa04.json.Volley;

public class MessageReceiver {

  ObjectMapper mapper;
  Player player;

  MessageReceiver(Player player) {
    this.mapper = new ObjectMapper();
    this.player = player;
  }

  public JsonNode receiveMessage(MessageJson message) throws JsonProcessingException {
    return switch (message.methodName()) {
      case "join" -> this.handleJoin();
      case "setup" -> this.handleSetup(message.arguments());
      case "take-shots" -> this.handleTakeShots();
      case "report-damage" -> this.handleReportDamage(message.arguments());
      case "successful-hits" -> this.handleSuccessfulHits(message.arguments());
      case "end-game" -> this.handleEndGame(message.arguments());
      default -> throw new IllegalStateException("Invalid message received");
    };
  }

  private JsonNode handleJoin() {
    JoinMessage joinArgs = new JoinMessage("aaa", GameType.SINGLE);

    JsonNode args = this.serializeRecord(joinArgs);
    return this.serializeRecord(new MessageJson("join", args));
  }

  private JsonNode handleSetup(JsonNode arguments) throws JsonProcessingException {
    SetupRequestMessage setup = this.mapper.treeToValue(arguments, SetupRequestMessage.class);

    Fleet ships = new Fleet(this.player.setup(setup.width(), setup.height(), setup.fleetSpec()));

    JsonNode args = this.serializeRecord(ships);
    return this.serializeRecord(new MessageJson("setup", args));
  }

  private JsonNode handleTakeShots() {
    Volley coords = new Volley(this.player.takeShots());

    JsonNode args = this.serializeRecord(coords);
    return this.serializeRecord(new MessageJson("take-shots", args));
  }

  private JsonNode handleReportDamage(JsonNode arguments) {
    Volley coords = this.mapper.convertValue(arguments, Volley.class);
    Volley result = new Volley(this.player.reportDamage(coords.coordinates()));

    JsonNode args = this.serializeRecord(result);
    return this.serializeRecord(new MessageJson("report-damage", args));
  }

  private JsonNode handleSuccessfulHits(JsonNode arguments) {
    Volley coords = this.mapper.convertValue(arguments, Volley.class);
    this.player.successfulHits(coords.coordinates());

    return this.serializeRecord(new MessageJson("successful-hits",
        new ObjectMapper().getNodeFactory().missingNode()));
  }

  private JsonNode handleEndGame(JsonNode arguments) throws JsonProcessingException {
    EndGameRequestMessage endgame = this.mapper.treeToValue(arguments, EndGameRequestMessage.class);
    this.player.endGame(endgame.result(), endgame.reason());

    return this.serializeRecord(new MessageJson("end-game",
        new ObjectMapper().getNodeFactory().missingNode()));
  }

  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  private JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    try {
      return this.mapper.convertValue(record, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Given record cannot be serialized");
    }
  }
}
