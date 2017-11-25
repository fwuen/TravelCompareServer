package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.AirportResource;

@Getter
@ToString
@EqualsAndHashCode
public class AirportsResponse {
    @JsonProperty(value = "AirportResource")
    private AirportResource airportResource;
}
