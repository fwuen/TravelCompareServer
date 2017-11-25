package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class FlightStatusResource {
    @JsonProperty(value = "Flights")
    private Flights flights;

    @JsonProperty(value = "Meta")
    private Meta meta;
}
