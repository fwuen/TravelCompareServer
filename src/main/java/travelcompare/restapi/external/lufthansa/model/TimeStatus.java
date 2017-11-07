package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeStatus
{
    @JsonProperty(value = "Code")
    private String code;

    @JsonProperty(value = "Definition")
    private String definition;
}