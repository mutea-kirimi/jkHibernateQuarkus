package domain.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UnitType {
    @JsonProperty("m")
    METRIC,
    @JsonProperty("s")
    SCIENTIFIC,
    @JsonProperty("f")
    FAHRENHEIT
}
