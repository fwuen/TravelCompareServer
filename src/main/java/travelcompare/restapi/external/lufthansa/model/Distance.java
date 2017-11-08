package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Distance {
    @JsonProperty(value = "Value")
    private long distanceValue;

    @JsonProperty(value = "UOM")
    private String uom;
}
