package domain.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
* By using this annotation, we're simply telling Jackson to map the value of the @JsonProperty to the object annotated with this value
*/

public enum RequestType {
    @JsonProperty("city")
    CITY,
    @JsonProperty("zipcode")
    ZIPCODE;

    @Override
    public String toString() {
        return super.toString();
    }
}
