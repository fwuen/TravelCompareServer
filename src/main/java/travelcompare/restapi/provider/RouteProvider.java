package travelcompare.restapi.provider;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

public interface RouteProvider {
    public Route getRoute(Geo start, Geo destination);
}
