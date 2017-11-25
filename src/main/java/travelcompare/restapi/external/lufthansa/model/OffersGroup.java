package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OffersGroup {
    @JsonProperty(value = "AirlineOffers")
    private AirlineOffers airlineOffers;
}
