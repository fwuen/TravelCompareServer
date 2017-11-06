package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
