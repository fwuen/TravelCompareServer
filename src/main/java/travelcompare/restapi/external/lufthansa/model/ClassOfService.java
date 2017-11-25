package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ClassOfService {
    @JsonProperty(value = "Code")
    private Code code;
    
    @JsonProperty(value = "MarketingName")
    private String marketingName;
}
