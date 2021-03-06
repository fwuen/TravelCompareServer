package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AnonymousTraveler {
    @JsonProperty(value = "@ObjectKey")
    private String objectKey;
    
    @JsonProperty(value = "PTC")
    private PTC ptc;
}
