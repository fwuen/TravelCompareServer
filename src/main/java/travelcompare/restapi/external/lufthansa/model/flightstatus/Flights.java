package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flights {
    @JsonProperty(value = "Flight")
    private Flight flight;
}
