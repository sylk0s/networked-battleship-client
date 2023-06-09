package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Represents a volley shot by some player
 *
 * @param coordinates The list of coordinates being shot at
 */
public record Volley(
    @JsonProperty("coordinates") List<Coord> coordinates
) {

}