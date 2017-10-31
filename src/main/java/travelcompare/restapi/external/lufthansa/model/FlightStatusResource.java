package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlightStatusResource {
    @JsonProperty(value = "Flights")
    private List<Flight> flights;
}
