package travelcompare.restapi.provider.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class TrainStation extends Geo {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String city;

    public TrainStation(double lat, double lon) {
        super(lat, lon);
    }

}
