package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Warnings {
    @JsonProperty(value = "Warning")
    private Warning warning;
}
