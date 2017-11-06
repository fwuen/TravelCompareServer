package travelcompare.restapi.external.lufthansa.model.flightstatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EstimatedTimeLocal {
    @JsonProperty(value = "EstimatedTimeLocal")
    private Date dateTime;
}
