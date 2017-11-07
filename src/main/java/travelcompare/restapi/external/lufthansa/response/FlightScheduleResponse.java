package travelcompare.restapi.external.lufthansa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import travelcompare.restapi.external.lufthansa.model.ScheduleResource;

@Getter
@ToString
@EqualsAndHashCode
public class FlightScheduleResponse {
    @JsonProperty(value = "ScheduleResource")
    private ScheduleResource scheduledResource;
}
