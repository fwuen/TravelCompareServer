package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PTC {
    @JsonProperty(value = "@Quantity")
    private String quantity;
    
    @JsonProperty(value ="$")
    private String value;
}
