package travelcompare.restapi.provider.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
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
    private long duration;

    @Getter
    @Setter
    @NonNull
    private Transport transport;

}
