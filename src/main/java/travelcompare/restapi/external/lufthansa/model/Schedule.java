package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@EqualsAndHashCode
@ToString
public class Schedule {
    @JsonProperty(value = "TotalJourney")
    private TotalJourney totalJourney;

    @JsonProperty(value = "Flight")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Flight> flight;
}
