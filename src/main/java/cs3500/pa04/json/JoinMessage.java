package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The message to return to the server when joining a new game
 *
 * @param name The name of the player
 * @param gameType THe type of the game to join
 */
public record JoinMessage(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType
) {
}
