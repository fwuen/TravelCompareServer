package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Names {
    @JsonProperty(value = "Name")
    private List<Name> names;
}
