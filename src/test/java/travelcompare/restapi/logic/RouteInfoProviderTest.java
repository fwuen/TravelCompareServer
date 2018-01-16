package travelcompare.restapi.logic;

import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.api.model.request.RouteType;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;
import java.util.Optional;

public class RouteInfoProviderTest {

    private static final Geo START = new Geo(50.21932, 11.943976);
    private static final Geo DESTINATION = new Geo(52.500232, 13.310086);

    @Test
    @Ignore
    public void blub() throws Exception {
        RouteProvider provider = new RouteProvider();

        //Optional<RouteInfo> route = provider.findCarRoute(START, DESTINATION, new Date(), FuelType.ALL);

        //System.out.println(route.isPresent() ? "car: " + route.get().toString() : "Kein RouteInfo gefunden");

        //route = provider.findAirportRoute(START, DESTINATION, 10000, new Date(), FuelType.ALL);

        //System.out.println(route.isPresent() ? "airport: " + route.get().toString() : "Kein RouteInfo gefunden");

        Optional<Route> route = provider.findTrainRoute(START, DESTINATION, 10000, new Date(), FuelType.ALL, RouteType.FASTEST);

        System.out.println(route.isPresent() ? "train: " + route.get().toString() : "Kein RouteInfo gefunden");
    }

}
