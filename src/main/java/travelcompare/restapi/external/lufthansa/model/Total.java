package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Total {
    @JsonProperty(value = "@Code")
    private String code;
    
    @JsonProperty(value = "$")
    private String value;
}
