package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Airports {
    @JsonProperty(value = "Airport")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Airport> airport;

    @JsonProperty(value = "Meta")
    private Meta meta;
}
