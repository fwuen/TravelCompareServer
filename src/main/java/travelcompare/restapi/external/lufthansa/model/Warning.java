package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Warning {
    @JsonProperty(value = "Catalogue")
    private String catalogue;
    
    @JsonProperty(value = "ProcessingError")
    private ProcessingError processingError;
}
