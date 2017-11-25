package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class FaresResponse {
    @JsonProperty(value = "AirShoppingRS")
    private AirShoppingResponse airShoppingResponse;
    
    @JsonProperty(value = "Meta")
    private Meta meta;
}
