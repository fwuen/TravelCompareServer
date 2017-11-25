package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FlightSegmentList {
    @JsonProperty(value = "FlightSegment")
    private List<FlightSegment> flightSegmentList;
}
