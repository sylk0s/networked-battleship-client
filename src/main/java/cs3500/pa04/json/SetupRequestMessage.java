package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * The message from the server for setup
 *
 * @param width The width of the board
 * @param height The height of the board
 * @param fleetSpec The spec of the fleet
 */
public record SetupRequestMessage(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {

}