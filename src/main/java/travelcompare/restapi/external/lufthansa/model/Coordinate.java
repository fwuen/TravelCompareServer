package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Coordinate {
    @JsonProperty(value = "Latitude")
    private double latitude;

    @JsonProperty(value = "Longitude")
    private double longitude;
}
