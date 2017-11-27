package travelcompare.restapi.provider;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;

public class CarRouteProvider implements RouteProvider {
    @Override
    public Route getRoute(Geo start, Geo destination) {
        return null;
    }
    
    @Override
    public Route getRouteOnDate(Geo start, Geo destination, Date travelDate) throws Exception {
        return null;
    }
}
