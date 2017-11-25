package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OfferPrice {
    @JsonProperty(value = "@OfferItemID")
    private String offerItemId;
    
    @JsonProperty(value = "RequestedDate")
    private RequestedDate requestedDate;
    
    @JsonProperty(value = "FareDetail")
    private FareDetail fareDetail;
}
