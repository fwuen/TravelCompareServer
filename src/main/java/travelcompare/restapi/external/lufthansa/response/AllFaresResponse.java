package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.FaresResponse;

@Getter
@EqualsAndHashCode
@ToString
public class AllFaresResponse {
    @JsonProperty(value = "FaresResponse")
    private FaresResponse faresResponse;
}
