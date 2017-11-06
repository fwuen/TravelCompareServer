package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Equipment {
    @JsonProperty(value = "AircraftCode")
    private String aircraftCode;
}
