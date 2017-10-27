package travelcompare.restapi.model.RouteRequest;

import lombok.Getter;
import lombok.Setter;

public class RouteRequest {

    @Getter
    @Setter
    private String start;

    @Getter
    @Setter
    private String destination;

    @Getter
    @Setter
    private RouteResultType routeResultType;
}
