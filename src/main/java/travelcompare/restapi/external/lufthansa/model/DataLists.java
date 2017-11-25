package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataLists {
    @JsonProperty(value = "AnonymousTravelerList")
    private AnonymousTravelerList anonymousTravelerList;
    
    @JsonProperty(value = "FlightSegmentList")
    private FlightSegmentList flightSegmentList;
}
