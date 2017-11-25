package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TimeStatus
{
    @JsonProperty(value = "Code")
    private String code;

    @JsonProperty(value = "Definition")
    private String definition;
}
