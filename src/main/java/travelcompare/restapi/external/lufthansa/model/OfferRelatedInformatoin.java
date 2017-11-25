package travelcompare.restapi.external.lufthansa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class OfferRelatedInformatoin {
    @JsonProperty(value = "ApplicableFlight")
    private ApplicableFlight applicableFlight;
    
    @JsonProperty(value = "OtherAssociation")
    private OtherAssociation otherAssociation;
}
