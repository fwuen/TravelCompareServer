package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightSegment {
    @JsonProperty(value = "@SegmentKey")
    private String segmentKey;
    
    @JsonProperty(value = "Departure")
    private Departure departure;
    
    @JsonProperty(value = "Arrival")
    private Arrival arrival;
    
    @JsonProperty(value = "MarketingCarrier")
    private Carrier marketingCarrier;
}
