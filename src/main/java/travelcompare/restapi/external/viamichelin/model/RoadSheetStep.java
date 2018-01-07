package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class RoadSheetStep {

    public RoadSheetStep() {
        coords = new RouteCoords();
    }

    @Getter
    @Setter
    private double duration;

    @Getter
    @Setter
    private String instructions;

    @Getter
    @Setter
    private Long distance;

    @Getter
    @Setter
    private Long level;

    @Getter
    @Setter
    private Long partialDuration;

    @Getter
    @Setter
    private Long gathering;

    @Getter
    @Setter
    private Double partialDistance;

    @Getter
    @Setter
    private RouteCoords coords;

    @Getter
    @Setter
    private String picto;
}
