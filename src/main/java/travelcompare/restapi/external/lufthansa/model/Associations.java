package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Associations {
    @JsonProperty(value = "AssociatedTraveler")
    private AssociatedTraveler associatedTraveler;
}
