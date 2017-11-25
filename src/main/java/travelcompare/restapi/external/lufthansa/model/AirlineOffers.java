package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AirlineOffers {
    @JsonProperty(value = "TotalOfferQuantity")
    private int totalOfferQuantity;
    
    @JsonProperty(value = "AirlineOfferSnapshot")
    private AirlineOfferSnapshot airlineOfferSnapshot;
    
    @JsonProperty(value = "Owner")
    private String owner;
    
    @JsonProperty(value = "AirlineOffer")
    private List<AirlineOffer> airlineOfferList;
}
