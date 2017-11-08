package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    @JsonProperty(value = "Coordinate")
    private Coordinate coordinate;
}
