package travelcompare.restapi.logic;

import org.junit.Test;
import travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.Optional;

public class CheapestWayProviderTest {

    private static final Geo START = new Geo(50.21932, 11.943976);
    private static final Geo DESTINATION = new Geo(52.500232, 13.310086);

    @Test
    public void blub() throws Exception {
        CheapestWayProvider provider = new CheapestWayProvider();

        //Optional<Way> way = provider.findCheapestCarWay(START, DESTINATION, new Date(), FUEL_TYPE.all);

        //System.out.println(way.isPresent() ? "car: " + way.get().toString() : "Kein Way gefunden");

        //way = provider.findCheapestAirportWay(START, DESTINATION, 10000, new Date(), FUEL_TYPE.all);

        //System.out.println(way.isPresent() ? "airport: " + way.get().toString() : "Kein Way gefunden");

        Optional<Way> way = provider.findCheapestTrainWay(START, DESTINATION, 10000, new Date(), FUEL_TYPE.all);

        System.out.println(way.isPresent() ? "train: " + way.get().toString() : "Kein Way gefunden");
    }

}
