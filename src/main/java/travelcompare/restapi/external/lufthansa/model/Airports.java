package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Airports {
    @JsonProperty(value = "Airport")
    private List<Airport> airports;
}
