package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JoinMessage(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType
) {
}
