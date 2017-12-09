package travelcompare.restapi.provider;


import com.google.maps.errors.ApiException;
import org.junit.Assert;
import org.junit.Test;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;
import travelcompare.restapi.provider.perimeter.PerimeterSearchProvider;

import java.io.IOException;
import java.util.ArrayList;

public class PerimeterSearchProviderTest {

    private final Geo HOF;

    public PerimeterSearchProviderTest() {
        HOF = new Geo(50.31297, 11.91261);
    }

    @Test

    public void test() throws InterruptedException, ApiException, IOException {
        PerimeterSearchProvider perimeterSearchProvider = new PerimeterSearchProvider();

        ArrayList<TrainStation> results = perimeterSearchProvider.find(HOF);

        Assert.assertFalse(results.isEmpty());
        for (TrainStation trainStation :
                results) {
            Assert.assertNotNull(trainStation.getName());
        }
    }

    @Test
    public void testWitchRadius() throws InterruptedException, ApiException, IOException {
        PerimeterSearchProvider perimeterSearchProvider = new PerimeterSearchProvider();

        ArrayList<TrainStation> results = perimeterSearchProvider.find(HOF, 50000);

        Assert.assertFalse(results.isEmpty());
        for (TrainStation trainStation :
                results) {
            Assert.assertNotNull(trainStation.getName());
        }
    }
}
