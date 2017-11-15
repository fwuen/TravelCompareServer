package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferRelatedInformatoin {
    @JsonProperty(value = "ApplicableFlight")
    private ApplicableFlight applicableFlight;
    
    @JsonProperty(value = "OtherAssociation")
    private OtherAssociation otherAssociation;
}
