package travelcompare.restapi.provider.model;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Geo {

    public Geo(
            double lat,
            double lon
    ) {
        Preconditions.checkArgument(lat > -85 && lat < 85);
        Preconditions.checkArgument(lon > -180 && lat < 180);

        this.lat = lat;
        this.lon = lon;
    }

    @Getter
    protected double lat;

    @Getter
    protected double lon;

}
