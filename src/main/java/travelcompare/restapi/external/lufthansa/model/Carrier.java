package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Carrier {
    @JsonProperty(value = "AirlineID")
    private String airlineId;

    @JsonProperty(value = "FlightNumber")
    private String flightNumber;
}
