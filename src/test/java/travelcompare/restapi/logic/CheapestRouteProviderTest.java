package travelcompare.restapi.logic;

import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;
import java.util.Optional;

public class CheapestRouteProviderTest {

    private static final Geo START = new Geo(50.21932, 11.943976);
    private static final Geo DESTINATION = new Geo(52.500232, 13.310086);

    @Test
    @Ignore
    public void blub() throws Exception {
        CheapestWayProvider provider = new CheapestWayProvider();

        //Optional<Route> way = provider.findCheapestCarWay(START, DESTINATION, new Date(), FuelType.all);

        //System.out.println(way.isPresent() ? "car: " + way.get().toString() : "Kein Route gefunden");

        //way = provider.findCheapestAirportWay(START, DESTINATION, 10000, new Date(), FuelType.all);

        //System.out.println(way.isPresent() ? "airport: " + way.get().toString() : "Kein Route gefunden");

        Optional<Route> way = provider.findCheapestTrainWay(START, DESTINATION, 10000, new Date(), FuelType.ALL);

        System.out.println(way.isPresent() ? "train: " + way.get().toString() : "Kein Route gefunden");
    }

}
