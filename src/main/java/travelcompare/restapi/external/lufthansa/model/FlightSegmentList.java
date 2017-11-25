package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@EqualsAndHashCode
@ToString
public class FlightSegmentList {
    @JsonProperty(value = "FlightSegment")
    private List<FlightSegment> flightSegmentList;
}
