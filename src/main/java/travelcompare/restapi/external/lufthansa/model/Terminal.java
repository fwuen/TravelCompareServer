package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Terminal {
    private String name;
    @JsonIgnore
    private String gate;
}
