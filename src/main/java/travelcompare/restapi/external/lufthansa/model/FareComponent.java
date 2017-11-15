package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class FareComponent {
    @JsonProperty(value = "AirlineID")
    private String airlineId;
    
    @JsonProperty(value = "DepartureCode")
    private String departureCode;
    
    @JsonProperty(value = "DepartureDate")
    private Date departureDate;
    
    @JsonProperty(value = "ArrivalCode")
    private String arrivalCode;
    
    @JsonProperty(value = "FareBasis")
    private FareBasis fareBasis;
}
