package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Taxes {
    @JsonProperty(value = "Total")
    private Total total;
}
