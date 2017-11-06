package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleResource {
    @JsonProperty(value = "Schedule")
    private List<Schedule> schedule;
}
