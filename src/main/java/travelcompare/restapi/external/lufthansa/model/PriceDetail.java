package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PriceDetail {
    @JsonProperty(value = "TotalAmount")
    private TotalAmount totalAmount;
    
    @JsonProperty(value = "BaseAmount")
    private BaseAmount baseAmount;
    
    @JsonProperty(value = "Taxes")
    private Taxes taxes;
}
