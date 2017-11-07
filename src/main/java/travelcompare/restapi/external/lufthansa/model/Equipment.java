package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Equipment {
    @JsonProperty(value = "AircraftCode")
    private String aircraftCode;

    @JsonProperty(value = "OnBoardEquipment")
    @JsonIgnoreProperties
    private OnboardEquipment onboardEquipment;
}
