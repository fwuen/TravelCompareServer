package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class NodePath {
    @JsonProperty(value = "Path")
    private Path path;
    
    @JsonProperty(value = "TagName")
    private TagName tagName;
}
