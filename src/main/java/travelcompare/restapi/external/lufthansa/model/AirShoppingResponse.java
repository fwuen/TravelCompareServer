package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AirShoppingResponse {
    @JsonProperty(value = "Document")
    private Document document;
    
    @JsonProperty(value = "AirShoppingProcessing")
    private AirShoppingProcessing airShoppingResponseProcessing;
    
    @JsonProperty(value = "ShoppingResponseIDs")
    private ShoppingResponseIds shoppingResponseIds;
    
    @JsonProperty(value = "OffersGroup")
    private OffersGroup offersGroup;
    
    @JsonProperty(value = "DataLists")
    private DataLists dataLists;
}
