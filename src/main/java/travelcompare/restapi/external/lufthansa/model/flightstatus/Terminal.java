package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Terminal {
    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Gate")
    @JsonIgnoreProperties
    private String gate;
}
