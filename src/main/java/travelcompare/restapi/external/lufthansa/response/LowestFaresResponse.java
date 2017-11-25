package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class LowestFaresResponse {
    @JsonProperty(value = "LowestFaresResponse")
    private travelcompare.restapi.external.lufthansa.model.LowestFaresResponse lowestFaresResponse;
}
