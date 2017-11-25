package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalAmount {
    @JsonProperty(value = "SimpleCurrencyPrice")
    private SimpleCurrencyPrice simpleCurrencyPrice;
}
