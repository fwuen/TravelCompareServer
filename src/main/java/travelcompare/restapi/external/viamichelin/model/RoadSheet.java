package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class RoadSheet {
    public RoadSheet() {
        roadSheetStep = new ArrayList<>();
    }

    @Getter
    @Setter
    private ArrayList<RoadSheetStep> roadSheetStep;
}