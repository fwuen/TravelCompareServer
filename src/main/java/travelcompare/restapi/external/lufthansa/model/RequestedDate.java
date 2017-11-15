package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedDate {
    @JsonProperty(value = "PriceDetail")
    private PriceDetail priceDetail;
    
    @JsonProperty(value = "Associations")
    private Associations associations;
}
