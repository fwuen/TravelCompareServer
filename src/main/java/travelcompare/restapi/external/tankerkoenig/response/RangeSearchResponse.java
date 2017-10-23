package travelcompare.restapi.external.tankerkoenig.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class RangeSearchResponse extends Response {

    private String status;

    private List<Station> stations;

}
