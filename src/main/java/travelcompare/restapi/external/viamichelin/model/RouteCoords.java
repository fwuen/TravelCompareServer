package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RouteCoords {

    @Getter
    @Setter
    private Double long_coordinate;

    @Getter
    @Setter
    private Double lat_coordinate;
}
