package travelcompare.restapi.provider.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Route {
    @Getter
    @Setter
    @NonNull
    private Geo start;

    @Getter
    @Setter
    @NonNull
    private Geo destination;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private int duration;

    @Getter
    @Setter
    @NonNull
    private Transport transport;
}
