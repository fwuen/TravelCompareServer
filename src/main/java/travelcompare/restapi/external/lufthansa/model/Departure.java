package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure {
    @JsonProperty(value = "AirportCode")
    private String airportCode;

    @JsonProperty(value = "ScheduledTimeLocal")
    private ScheduledTimeLocal scheduledTimeLocal;

    @JsonProperty(value = "ScheduledTimeUTC")
    private ScheduledTimeUtc scheduledTimeUtc;

    @JsonProperty(value = "ActualTimeLocal")
    private ActualTimeLocal actualTimeLocal;

    @JsonProperty(value = "ActualTimeUTC")
    private ActualTimeUtc actualTimeUtc;

    @JsonProperty(value = "TimeStatus")
    private TimeStatus timeStatus;

    @JsonProperty(value = "Terminal")
    private Terminal terminal;
}
