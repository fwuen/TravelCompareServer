package travelcompare.restapi.provider;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import static travelcompare.restapi.provider.model.Transport.AIRCRAFT;

public class AircraftRouteProvider implements RouteProvider {

    @Override
    public Route getRoute(Geo start, Geo destination) {
        Route aircraftRoute = new Route();
        aircraftRoute.setStart(getStart());
        aircraftRoute.setDestination(getDestination());
        //aircraftRoute.setDuration();
        //aircraftRoute.setPrice();
        aircraftRoute.setTransport(AIRCRAFT);

        return aircraftRoute;
    }

    private Geo getStart() {
        //Airport airport = new Airport();
        return null;
    }

    private Geo getDestination() {
        return null;
    }
}
