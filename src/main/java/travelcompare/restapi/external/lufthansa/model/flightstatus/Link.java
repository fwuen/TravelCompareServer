package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
    @JsonProperty(value = "@Href")
    private String href;

    @JsonProperty(value = "@Rel")
    private String rel;
}
