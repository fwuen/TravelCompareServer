package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnonymousTraveler {
    @JsonProperty(value = "@ObjectKey")
    private String objectKey;
    
    @JsonProperty(value = "PTC")
    private PTC ptc;
}
