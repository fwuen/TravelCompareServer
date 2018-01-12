package travelcompare.restapi.external.bahnprice;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

public class BahnPriceConsumerTest {

    @Test
    public void testGetPrice() throws UnirestException {
        new BahnPriceConsumer().getBahnPrice("10000","ICE");
    }
}
