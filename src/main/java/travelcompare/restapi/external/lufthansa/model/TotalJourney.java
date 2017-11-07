package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalJourney {
    @JsonProperty(value = "Duration")
    private String duration;
}
