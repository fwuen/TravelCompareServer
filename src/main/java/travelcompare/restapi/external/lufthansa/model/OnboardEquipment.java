package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OnboardEquipment {
    @JsonProperty(value = "InflightEntertainment")
    private boolean inflightEntertainment;

    @JsonProperty(value = "Compartment")
    private List<Compartment> compartment;
}
