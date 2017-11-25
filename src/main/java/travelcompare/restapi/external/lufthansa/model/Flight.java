package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Flight {
    @JsonProperty(value = "Departure")
    private Departure departure;

    @JsonProperty(value = "Arrival")
    private Arrival arrival;

    @JsonProperty(value = "MarketingCarrier")
    private Carrier marketingCarrier;

    @JsonProperty(value = "OperatingCarrier")
    private Carrier operatingCarrier;

    @JsonProperty(value = "Equipment")
    private Equipment equipment;

    @JsonProperty(value = "FlightStatus")
    private FlightStatus flightStatus;

    @JsonProperty(value = "Details")
    private Details details;
}
