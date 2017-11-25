package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Getter
@EqualsAndHashCode
@ToString
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
