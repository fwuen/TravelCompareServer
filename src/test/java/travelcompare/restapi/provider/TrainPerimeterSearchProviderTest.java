package travelcompare.restapi.provider;


import com.google.maps.errors.ApiException;
import org.junit.Assert;
import org.junit.Test;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;
import java.util.ArrayList;

public class TrainPerimeterSearchProviderTest {

    private final Geo HOF;

    public TrainPerimeterSearchProviderTest() {
        HOF = new Geo(50.31297, 11.91261);
    }

    @Test

    public void test() throws InterruptedException, ApiException, IOException {
        TrainPerimeterSearchProvider trainPerimeterSearchProvider = new TrainPerimeterSearchProvider();

        ArrayList<TrainStation> results = trainPerimeterSearchProvider.findTrainstations(HOF);

        Assert.assertFalse(results.isEmpty());
        for (TrainStation trainStation :
                results) {
            Assert.assertNotNull(trainStation.getName());
            Assert.assertNotNull(trainStation.getLat());
            Assert.assertNotNull(trainStation.getLon());
        }
    }

    @Test
    public void testWitchRadius() throws InterruptedException, ApiException, IOException {
        TrainPerimeterSearchProvider trainPerimeterSearchProvider = new TrainPerimeterSearchProvider();

        ArrayList<TrainStation> results = trainPerimeterSearchProvider.findTrainstations(HOF, 50000);

        Assert.assertFalse(results.isEmpty());
        for (TrainStation trainStation :
                results) {
            Assert.assertNotNull(trainStation.getName());
            Assert.assertNotNull(trainStation.getLat());
            Assert.assertNotNull(trainStation.getLon());
        }
    }
}
