package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PTC {
    @JsonProperty(value = "@Quantity")
    private String quantity;
    
    @JsonProperty(value ="$")
    private String value;
}
