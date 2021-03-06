package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ProcessingError {
    @JsonProperty(value = "@RetryIndicator")
    private boolean retyIndicator;
    
    @JsonProperty(value = "Type")
    private String ResrouceNotFound;
    
    @JsonProperty(value = "Code")
    private int code;
    
    @JsonProperty(value = "Description")
    private String description;
    
    @JsonProperty(value = "InfoURL")
    private String infoUrl;
    
    @JsonProperty(value ="NodePath")
    private NodePath nodePath;
}
