package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import travelcompare.restapi.external.lufthansa.model.AirportResource;

public class AirportsResponse {
    @JsonProperty(value = "AirportResource")
    private AirportResource airportResource;
}
