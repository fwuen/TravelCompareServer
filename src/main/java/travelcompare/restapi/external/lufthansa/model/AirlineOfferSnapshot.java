package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AirlineOfferSnapshot {
    @JsonProperty(value = "@DateTime")
    private Date dateTime;
}