package travelcompare.restapi.provider;

import com.google.maps.errors.ApiException;
import org.junit.Assert;
import org.junit.Test;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;

public class TrainRouteProviderTest {

    private final TrainStation HOF_HBF;
    private final TrainStation OBERKOTZAU;

    public TrainRouteProviderTest() {
        HOF_HBF = new TrainStation(50.308240, 11.923380)
                .setCity("Hof")
                .setName("Hof Hbf");

        OBERKOTZAU = new TrainStation(50.266389, 11.932204)
                .setCity("Oberkotzau")
                .setName("Oberkotzau Bahnhof");
    }

    @Test
    public void testWithValidData() throws InterruptedException, ApiException, IOException, NoRouteFoundException {
        TrainRouteProvider provider = new TrainRouteProvider();

        Route result = provider.getRoute(HOF_HBF, OBERKOTZAU);

        Assert.assertNotNull(result.getStart());
        Assert.assertNotNull(result.getDestination());
        Assert.assertNotNull(result.getRouteRepresentation());

    }

}
