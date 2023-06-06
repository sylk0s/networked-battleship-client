package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

public record EndGameRequestMessage(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
    ) {
}
