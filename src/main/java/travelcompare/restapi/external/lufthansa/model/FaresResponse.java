package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaresResponse {
    @JsonProperty(value = "AirShoppingRS")
    private AirShoppingResponse airShoppingResponse;
    
    @JsonProperty(value = "Meta")
    private Meta meta;
}
