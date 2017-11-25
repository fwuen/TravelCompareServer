package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Summary {
    public Summary() {
        fullMapDef = new MapDef();
        CCZCost = new Cost();
        tollCost = new Cost();
        names = new ArrayList<>();
    }

    @Getter
    @Setter
    private Double ecoTaxDist;

    @Getter
    @Setter
    private MapDef fullMapDef;

    @Getter
    @Setter
    private Double motorwayTime;

    @Getter
    @Setter
    private Double pleasentDist;

    @Getter
    @Setter
    private Double totalTime;

    @Getter
    @Setter
    private Double pleasentTime;

    @Getter
    @Setter
    private Long index;

    @Getter
    @Setter
    private Double drivingTime;

    @Getter
    @Setter
    private Double consumption;

    @Getter
    @Setter
    private String distanceByCountry;

    @Getter
    @Setter
    private Cost CCZCost;

    @Getter
    @Setter
    private Cost tollCost;

    @Getter
    @Setter
    private Double ecoTaxRepercussionRate;

    @Getter
    @Setter
    private boolean avoidClosedRoadUsed;

    @Getter
    @Setter
    private ArrayList<String> names;

    @Getter
    @Setter
    private boolean eventTrafficDatabaseAvailable;

    @Getter
    @Setter
    private Double drivingDist;

    @Getter
    @Setter
    private Double totalDist;

    @Getter
    @Setter
    private Double ecoTax;

    @Getter
    @Setter
    private Double motorwayDist;

}
