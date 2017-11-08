package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.NearestAirportResource;

@Getter
@ToString
@EqualsAndHashCode
public class NearestAirportResponse {
    @JsonProperty(value = "NearestAirportResource")
    private NearestAirportResource nearestAirportResource;
}
