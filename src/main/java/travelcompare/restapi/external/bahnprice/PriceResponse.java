package travelcompare.restapi.external.bahnprice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PriceResponse {

    @JsonProperty
    private double distance;

    @JsonProperty
    private double price;

    @JsonProperty
    private String type;

}
