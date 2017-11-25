package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferId {
    @JsonProperty(value = "@Owner")
    private String owner;
    
    @JsonProperty(value = "$")
    private String code;
}
