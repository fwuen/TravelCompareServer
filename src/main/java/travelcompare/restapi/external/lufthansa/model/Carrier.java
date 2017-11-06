package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Carrier {
    @JsonProperty(value = "AirlineID")
    private String airlineId;

    @JsonProperty(value = "FlightNumber")
    private String flightNumber;
}
