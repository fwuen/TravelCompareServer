package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class DataLists {
    @JsonProperty(value = "AnonymousTravelerList")
    private AnonymousTravelerList anonymousTravelerList;
    
    @JsonProperty(value = "FlightSegmentList")
    private FlightSegmentList flightSegmentList;
}
