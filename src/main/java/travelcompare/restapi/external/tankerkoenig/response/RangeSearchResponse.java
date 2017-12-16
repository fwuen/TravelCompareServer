package travelcompare.restapi.external.tankerkoenig.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class RangeSearchResponse {

    @JsonProperty
    private boolean ok;

    @JsonProperty
    private String license;

    @JsonProperty
    private String data;

    @JsonProperty
    private Optional<String> message;

    @JsonProperty
    private String status;

    @JsonProperty
    private List<Station> stations;

}
