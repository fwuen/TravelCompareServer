package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.flightstatus.FlightStatusResource;

@Getter
@ToString
@EqualsAndHashCode
public class FlightStatusResponse {
    @JsonProperty(value = "FlightStatusResource")
    private FlightStatusResource flightStatusResource;
}
