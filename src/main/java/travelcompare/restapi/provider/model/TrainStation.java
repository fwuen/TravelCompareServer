package travelcompare.restapi.provider.model;

import lombok.Getter;
import lombok.Setter;

public class TrainStation extends Geo {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String city;

}
