package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class AirlineOffer {
    @JsonProperty(value = "OfferID")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<OfferId> offerId;
    
    @JsonProperty(value = "TotalPrice")
    private TotalPrice totalPrice;
    
    @JsonProperty(value = "PricedOffer")
    private PricedOffer pricedOffer;
}
