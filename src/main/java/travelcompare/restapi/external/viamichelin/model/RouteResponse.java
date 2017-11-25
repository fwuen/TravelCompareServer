package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RouteResponse {
    public RouteResponse() {
        iti = new RouteIti();
    }

    @Getter
    @Setter
    private RouteIti iti;
}
