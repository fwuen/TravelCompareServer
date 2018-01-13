package travelcompare.restapi.provider;

import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Way;
import travelcompare.restapi.provider.way.CarWaysProvider;

import java.util.Date;
import java.util.List;

public class CarWaysProviderTest {
    /**
     * Test für den CarWaysProvider
     */
    @Test
    public void TestCarWaysProvider() {

        List<Way> ways = null;
        ways = new CarWaysProvider().findWithFuelType(new Geo(50.268186, 11.848444), new Geo(52.520645, 13.409779), new Date(), FuelType.DIESEL);
        Assert.notNull(ways);
        Assert.notEmpty(ways);
        Assert.isTrue(ways.size() == 2);
        Assert.notNull(ways.get(0).getRoutes());
        Assert.notEmpty(ways.get(0).getRoutes());
        Assert.isTrue(ways.get(0).getRoutes().size() > 0);
        Assert.isTrue(ways.get(0).getPrice() > 0);
        Assert.notNull(ways.get(1).getRoutes());
        Assert.notEmpty(ways.get(1).getRoutes());
        Assert.isTrue(ways.get(1).getRoutes().size() > 0);
        Assert.isTrue(ways.get(1).getPrice() > 0);

    }
}
