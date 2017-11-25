package travelcompare.restapi.provider;

import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.TrainStation;

public class TrainRouteProvider implements RouteProvider<TrainStation> {

    @Override
    public Route getRoute(TrainStation start, TrainStation destination) {
        return null;
    }
}
