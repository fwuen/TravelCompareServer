package travelcompare.restapi.provider.model;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;

public class Trainstation extends Geo {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String city;

    public Trainstation(double lat, double lon) {
        Preconditions.checkArgument(lat > -85 && lat < 85);
        Preconditions.checkArgument(lon > -180 && lat < 180);
        this.lat = lat;
        this.lon = lon;
    }
}
