package travelcompare.restapi.external.viamichelin;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import travelcompare.restapi.SpringTest;

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
}


