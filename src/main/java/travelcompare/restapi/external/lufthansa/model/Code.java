package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code {
    @JsonProperty(value = "@SeatsLeft")
    private String seatsLeft;
    
    @JsonProperty(value = "$")
    private String value;
}
