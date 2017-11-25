package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RouteIti {

    public RouteIti() {
        roadSheet = new RoadSheet();
        header = new RouteHeader();
    }

    @Getter
    @Setter
    private RoadSheet roadSheet;

    @Getter
    @Setter
    private String ititraceLevels;

    @Getter
    @Setter
    private RouteHeader header;

    @Getter
    @Setter
    private String itineraryTrace;
}
