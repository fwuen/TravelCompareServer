package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OtherAssocationInAssociation {
    @JsonProperty(value = "Type")
    private String type;
    
    @JsonProperty(value = "ReferenceValue")
    private String referenceValue;
}
