package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailCurrencyPrice {
    @JsonProperty(value = "Total")
    private Total total;
    
    @JsonProperty(value = "Taxes")
    private Taxes taxes;
}
