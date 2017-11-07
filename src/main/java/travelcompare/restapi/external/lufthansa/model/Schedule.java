package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schedule {
    @JsonProperty(value = "TotalJourney")
    private TotalJourney totalJourney;

    @JsonProperty(value = "Flight")
    private Flight flight;
}
