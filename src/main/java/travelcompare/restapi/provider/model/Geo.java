package travelcompare.restapi.provider.model;

import lombok.Getter;

public abstract class Geo {
    @Getter
    protected double lat;

    @Getter
    protected double lon;
}
