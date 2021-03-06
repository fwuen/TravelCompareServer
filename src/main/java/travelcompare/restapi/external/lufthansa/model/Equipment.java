package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Equipment {
    @JsonProperty(value = "AircraftCode")
    private String aircraftCode;

    @JsonProperty(value = "OnBoardEquipment")
    @JsonIgnoreProperties
    private OnboardEquipment onboardEquipment;
}
