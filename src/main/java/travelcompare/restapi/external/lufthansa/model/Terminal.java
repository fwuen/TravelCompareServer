package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Terminal {
    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Gate")
    @JsonIgnoreProperties
    private String gate;
}
