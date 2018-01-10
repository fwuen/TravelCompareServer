package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RouteHeader {
    public RouteHeader() {
        summaries = new Summary();
    }

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