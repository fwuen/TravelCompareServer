package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScheduleResource {
    @JsonProperty(value = "Schedule")
    private List<Schedule> schedule;
}
