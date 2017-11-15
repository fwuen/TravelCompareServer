package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicableFlight {
    @JsonProperty(value = "FlightSegmentReference")
    private FlightSegmentReference flightSegmentReference;
}
