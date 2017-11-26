package travelcompare.restapi.provider;

import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Route;

public class AircraftRouteProviderTest {
    @Ignore
    @Test
    public void testGetRoute() {
        AircraftRouteProvider provider = new AircraftRouteProvider();
        Airport start = new Airport(51.281111, 6.752777);
        Airport destination = new Airport(52.560277, 13.295555);
        Route route = null;
        try {
            route = provider.getRoute(start, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(route.getStart());
        System.out.println(route.getDestination());
        System.out.println(route.getDuration());
        System.out.println(route.getPrice());
    }
}
