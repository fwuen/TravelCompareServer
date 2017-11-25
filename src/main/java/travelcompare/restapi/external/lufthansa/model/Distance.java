package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Distance {
    @JsonProperty(value = "Value")
    private long distanceValue;

    @JsonProperty(value = "UOM")
    private String uom;
}
