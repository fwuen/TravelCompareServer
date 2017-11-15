package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PricedOffer {
    @JsonProperty(value = "OfferPrice")
    private OfferPrice offerPrice;
    
    @JsonProperty(value = "Associations")
    private OfferRelatedInformatoin associations;
}
