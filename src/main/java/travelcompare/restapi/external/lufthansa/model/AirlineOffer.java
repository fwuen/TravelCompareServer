package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirlineOffer {
    @JsonProperty(value = "OfferID")
    private OfferId offerId;
    
    @JsonProperty(value = "TotalPrice")
    private TotalPrice totalPrice;
    
    @JsonProperty(value = "PricedOffer")
    private PricedOffer pricedOffer;
}
