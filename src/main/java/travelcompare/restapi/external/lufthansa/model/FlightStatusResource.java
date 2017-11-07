package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightStatusResource {
    @JsonProperty(value = "Flights")
    private Flights flights;

    @JsonProperty(value = "Meta")
    private Meta meta;
}