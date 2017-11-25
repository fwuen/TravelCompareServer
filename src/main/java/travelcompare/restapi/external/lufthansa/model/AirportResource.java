package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AirportResource {
    @JsonProperty(value = "Airports")
    private Airports airports;

    @JsonProperty(value = "Meta")
    private Meta meta;
}
