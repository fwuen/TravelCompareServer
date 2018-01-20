package travelcompare.restapi.external.bahnprice;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;

public class BahnPriceConsumerTest {

    @Test
    public void testGetPrice() throws UnirestException {
        double price = new BahnPriceConsumer().getBahnPrice("10000", "ICE");
        Assert.isTrue(price > 1);
    }
}
