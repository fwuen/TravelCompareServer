package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
public class FlightStatus {
    
    //TODO JsonProperty value auch möglich, wenn Attribut innerhalb von Array?
    //TODO generell mal überprüfen
    
    @JsonProperty(required = true)
    private String departureAirport;
    
    private Date scheduledDepartureDateLocal;
    
    private Date scheduledDepartureDateUTC;
    
    private Date actualDepartureDateLocal;
    
    private Date actualDepartureDateUTC;
    
    private String departureTimeStatus;
    
    private String departureTerminal;
    
    @JsonProperty(required = true)
    private String arrivalAirport;
    
    private Date scheduledArrivalDateLocal;
    
    private Date scheduledArrivalDateUTC;
    
    private Date estimatedArrivalDateLocal;
    
    private Date estimatedArrivalDateUTC;
    
    private String arrivalTimeStatus;
    
    private String arrivalTerminal;
    
    private String airlineID;
    
    @JsonProperty(required = true)
    private String flightNumber;
    
    private String aircraftCode;
    
    @JsonProperty(required = true)
    private String flightStatus;
}
