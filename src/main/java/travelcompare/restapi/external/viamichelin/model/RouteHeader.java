package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RouteHeader {
    public RouteHeader() {
        startMapDef = new MapDef();
        destMapDef = new MapDef();
        summaries = new Summary();
    }

    @Getter
    @Setter
    private MapDef startMapDef;

    @Getter
    @Setter
    private MapDef destMapDef;

    @Getter
    @Setter
    private Long itiType;

    @Getter
    @Setter
    private String itidate;

    @Getter
    @Setter
    private Long idx;

    @Getter
    @Setter
    private Long vehicle;

    @Getter
    @Setter
    private Summary summaries;
}