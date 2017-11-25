package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirportResource {
    @JsonProperty(value = "Airports")
    private Airports airports;
}
