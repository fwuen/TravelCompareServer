package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import travelcompare.restapi.external.lufthansa.util.FlightDeserializer;

public class Schedule {
    @JsonProperty(value = "TotalJourney")
    private TotalJourney totalJourney;

    @JsonProperty(value = "Flight")
    @JsonDeserialize(using = FlightDeserializer.class)
    private Flight flight;
}
