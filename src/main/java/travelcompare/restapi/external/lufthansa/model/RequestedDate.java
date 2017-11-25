package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RequestedDate {
    @JsonProperty(value = "PriceDetail")
    private PriceDetail priceDetail;
    
    @JsonProperty(value = "Associations")
    private Associations associations;
}
