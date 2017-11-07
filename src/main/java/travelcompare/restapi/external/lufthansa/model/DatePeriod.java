package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatePeriod {
    @JsonProperty(value = "Effective")
    private String effective;

    @JsonProperty(value = "Expiration")
    private String expiration;
}
