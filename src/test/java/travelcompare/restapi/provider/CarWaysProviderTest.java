package travelcompare.restapi.provider;

import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.SpringTest;import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.way.CarWaysProvider;

import java.util.Date;
import java.util.List;

public class CarWaysProviderTest extends SpringTest{
    /**
     * Test für den CarWaysProvider
     */
    @Test
    public void TestCarWaysProvider() {

        List<Route> routes = null;
        routes = new CarWaysProvider().findWithFuelType(new Geo(50.268186, 11.848444), new Geo(52.520645, 13.409779), new Date(), FuelType.DIESEL);
        Assert.notNull(routes);
        Assert.notEmpty(routes);
        Assert.isTrue(routes.size() == 2);
        Assert.notNull(routes.get(0).getSteps());
        Assert.notEmpty(routes.get(0).getSteps());
        Assert.isTrue(routes.get(0).getSteps().size() > 0);
        Assert.isTrue(routes.get(0).getPrice() > 0);
        Assert.notNull(routes.get(1).getSteps());
        Assert.notEmpty(routes.get(1).getSteps());
        Assert.isTrue(routes.get(1).getSteps().size() > 0);
        Assert.isTrue(routes.get(1).getPrice() > 0);

    }
}
