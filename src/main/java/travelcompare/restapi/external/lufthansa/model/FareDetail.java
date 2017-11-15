package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FareDetail {
    @JsonProperty(value = "FareComponent")
    private FareComponent fareComponent;
}
