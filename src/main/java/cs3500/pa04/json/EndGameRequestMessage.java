package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * Message from the server on the end of a game
 *
 * @param result The result of the game
 * @param reason The reason the game ended
 */
public record EndGameRequestMessage(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
