package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Meta {
    @JsonProperty(value = "@Version")
    private String version;

    @JsonProperty(value = "Link")
    private List<Link> link;
}
