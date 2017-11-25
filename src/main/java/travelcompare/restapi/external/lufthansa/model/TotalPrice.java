package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalPrice {
    @JsonProperty(value = "DetailCurrencyPrice")
    private DetailCurrencyPrice detailCurrencyPrice;
}
