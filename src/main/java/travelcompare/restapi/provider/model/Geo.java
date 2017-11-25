package travelcompare.restapi.provider.model;

import lombok.*;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Geo {

    public Geo(
            double lat,
            double lon
    ) {
        this.lat = lat;
        this.lon = lon;
    }

    @Getter
    protected double lat;

    @Getter
    protected double lon;

}
