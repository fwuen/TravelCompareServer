package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stops {
    @JsonProperty(value = "StopQuantity")
    private int stopQuantity;
}
