package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Warning {
    @JsonProperty(value = "Catalogue")
    private String catalogue;
    
    @JsonProperty(value = "ProcessingError")
    private ProcessingError processingError;
}
