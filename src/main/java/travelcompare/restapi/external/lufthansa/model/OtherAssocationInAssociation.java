package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtherAssocationInAssociation {
    @JsonProperty(value = "Type")
    private String type;
    
    @JsonProperty(value = "ReferenceValue")
    private String referenceValue;
}
