package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApplicableFlight {
    @JsonProperty(value = "FlightSegmentReference")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<FlightSegmentReference> flightSegmentReference;
}
