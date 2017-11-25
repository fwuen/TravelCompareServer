package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LowestFaresResponse {
    @JsonProperty(value = "AirShoppingRS")
    private AirShoppingResponse airShoppingResponse;
    
    @JsonProperty(value = "Warnings")
    private Warnings warnings;
    
    @JsonProperty(value = "Meta")
    private Meta meta;
}
