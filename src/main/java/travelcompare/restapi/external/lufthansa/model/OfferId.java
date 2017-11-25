package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OfferId {
    @JsonProperty(value = "@Owner")
    private String owner;
    
    @JsonProperty(value = "$")
    private String code;
}
