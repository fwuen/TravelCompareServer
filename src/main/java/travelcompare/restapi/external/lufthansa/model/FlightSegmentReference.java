package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightSegmentReference {
    @JsonProperty(value = "@ref")
    private String ref;
    
    @JsonProperty(value = "ClassOfService")
    private ClassOfService classOfService;
}
