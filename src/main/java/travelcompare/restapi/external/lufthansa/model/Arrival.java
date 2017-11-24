package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Arrival {
    @JsonProperty(value = "AirportCode")
    private String airportCode;

    @JsonProperty(value = "ScheduledTimeLocal")
    private ScheduledTimeLocal scheduledTimeLocal;

    @JsonProperty(value = "ScheduledTimeUTC")
    private ScheduledTimeUtc scheduledTimeUtc;

    @JsonProperty(value = "EstimatedTimeLocal")
    private EstimatedTimeLocal estimatedTimeLocal;

    @JsonProperty(value = "EstimatedTimeUTC")
    private EstimatedTimeUtc estimatedTimeUtc;

    @JsonProperty(value = "ActualTimeLocal")
    private ActualTimeLocal actualTimeLocal;

    @JsonProperty(value = "ActualTimeUTC")
    private ActualTimeUtc actualTimeUtc;

    @JsonProperty(value = "TimeStatus")
    private TimeStatus timeStatus;

    @JsonProperty(value = "Terminal")
    private Terminal terminal;
    
    @JsonProperty(value = "Date")
    @JsonIgnoreProperties
    private Date date;
    
    @JsonProperty(value = "Time")
    @JsonIgnoreProperties
    private String time;
}
