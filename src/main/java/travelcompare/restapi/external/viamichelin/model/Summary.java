package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class Summary {

    @Getter
    @Setter
    private Double totalTime;

    @Getter
    @Setter
    private Double consumption;

    @Getter
    @Setter
    private Double totalDist;

}
