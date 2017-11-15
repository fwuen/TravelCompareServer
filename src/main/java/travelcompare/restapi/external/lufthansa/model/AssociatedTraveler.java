package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssociatedTraveler {
    @JsonProperty(value = "TravelerReferences")
    private String travelerReferences;
}
