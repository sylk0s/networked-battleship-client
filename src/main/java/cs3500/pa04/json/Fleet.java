package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;
import java.util.List;

/**
 * Represents a fleet of ships
 *
 * @param fleet the list of ships
 */
public record Fleet(@JsonProperty("fleet") List<Ship> fleet) {
}
