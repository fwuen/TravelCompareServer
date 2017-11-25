package travelcompare.restapi.external.viamichelin.model;

import lombok.Getter;
import lombok.Setter;

public class MapDef {
    public MapDef()
    {
        size = new MapSize();
    }
    @Getter
    @Setter
    private MapSize size;

    @Getter
    @Setter
    private String id;
}
