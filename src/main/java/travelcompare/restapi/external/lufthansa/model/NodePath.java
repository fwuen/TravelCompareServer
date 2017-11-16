package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodePath {
    @JsonProperty(value = "Path")
    private Path path;
    
    @JsonProperty(value = "TagName")
    private TagName tagName;
}
