package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EstimatedTimeLocal {
    @JsonProperty(value = "DateTime")
    private Date dateTime;
}
