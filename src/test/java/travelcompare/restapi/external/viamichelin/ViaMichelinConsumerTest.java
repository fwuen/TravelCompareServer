package travelcompare.restapi.external.viamichelin;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import travelcompare.restapi.SpringTest;

public class ViaMichelinConsumerTest extends SpringTest {

    /**
     * Test für den Geocoding_Enpunkt
     *
     * @throws UnirestException Exception
     */
    @Test
    public void TestGetGeoCoding() throws UnirestException {
        new ViaMichelinConsumer().getGeoCoding("DEU", "95176", null, null);
    }

    /**
     * Test für den Route-Endpunkt
     *
     * @throws UnirestException Exception
     */
    @Test
    public void TestGetRoute() throws UnirestException {
        new ViaMichelinConsumer().getRoute(11.848444, 50.268186, 11.942030, 50.325067);
    }
}

