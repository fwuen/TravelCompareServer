package travelcompare.restapi.logic;

import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.api.model.request.WayType;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.Optional;

public class WayProviderTest {

    private static final Geo START = new Geo(50.21932, 11.943976);
    private static final Geo DESTINATION = new Geo(52.500232, 13.310086);

    @Test
    @Ignore
    public void blub() throws Exception {
        WayProvider provider = new WayProvider();

        //Optional<Way> way = provider.findCarWay(START, DESTINATION, new Date(), FuelType.ALL);

        //System.out.println(way.isPresent() ? "car: " + way.get().toString() : "Kein Way gefunden");

        //way = provider.findAirportWay(START, DESTINATION, 10000, new Date(), FuelType.ALL);

        //System.out.println(way.isPresent() ? "airport: " + way.get().toString() : "Kein Way gefunden");

        Optional<Way> way = provider.findTrainWay(START, DESTINATION, 10000, new Date(), FuelType.ALL, WayType.FASTEST);

        System.out.println(way.isPresent() ? "train: " + way.get().toString() : "Kein Way gefunden");
    }

}