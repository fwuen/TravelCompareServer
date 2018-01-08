package travelcompare.restapi.external.viamichelin;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.way.CarWaysProvider;

import java.util.Date;

public class ViaMichelinConsumerTest extends SpringTest {

    /**
     * Test für das Ansprechen des Routen-Endpunkts von Michelin
     *
     * @throws UnirestException Exception
     */
    @Test
    public void TestGetRoute() throws UnirestException {
        new ViaMichelinConsumer().getRoute(11.848444, 50.268186, 11.942030, 50.325067, 1, 1.20, new Date());
    }


    /**
     * Test für den CarWaysProvider
     */
    @Test
    public void TestCarWaysProvider() {
        new CarWaysProvider().findWithFuelType(new Geo(50.268186, 11.848444), new Geo(52.520645, 13.409779), new Date(), FuelType.DIESEL);
    }
}


