package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@EqualsAndHashCode
@ToString
public class OnboardEquipment {
    @JsonProperty(value = "InflightEntertainment")
    private boolean inflightEntertainment;

    @JsonProperty(value = "Compartment")
    private List<Compartment> compartment;
}
