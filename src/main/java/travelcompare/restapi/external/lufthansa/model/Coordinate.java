package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    @JsonProperty(value = "Latitude")
    private double latitude;

    @JsonProperty(value = "Longitude")
    private double longitude;
}
