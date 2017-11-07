package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Details {
    @JsonProperty(value = "Stops")
    private Stops stops;

    @JsonProperty(value = "DaysOfOperation")
    private long daysOfOperation;

    @JsonProperty(value = "DatePeriod")
    private DatePeriod datePeriod;
}
