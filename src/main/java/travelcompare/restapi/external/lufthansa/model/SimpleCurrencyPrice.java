package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class SimpleCurrencyPrice {
    @JsonProperty(value = "@Code")
    private String code;
    
    @JsonProperty(value = "$")
    private String value;
}
