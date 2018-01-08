package travelcompare.restapi.external.tankerkoenig;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;

public class TankerkoenigConsumerTest extends SpringTest {

    @Test(expected = IllegalArgumentException.class)
    public void withLatitudeIsTooSmall() {
        new TankerkoenigConsumer().withLatitude(-181);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withLatitudeIsTooBig() {
        new TankerkoenigConsumer().withLatitude(181);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withLongitudeIsTooSmall() {
        new TankerkoenigConsumer().withLongitude(-181);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withLongitudeIsTooBig() {
        new TankerkoenigConsumer().withLatitude(181);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withRadiusIsZero() {
        new TankerkoenigConsumer().withRadiusInKilometers(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withRadiusIsTooBig() {
        new TankerkoenigConsumer().withRadiusInKilometers(26);
    }

    @Test(expected = NullPointerException.class)
    public void withFuelTypeIsNull() {
        new TankerkoenigConsumer().withFuelType(null);
    }

    @Test(expected = NullPointerException.class)
    public void withSortIsNull() {
        new TankerkoenigConsumer().withSort(null);
    }

    @Test(expected = IllegalStateException.class)
    public void withSortIsPriceWhileFuelTypeIsAll() {
        new TankerkoenigConsumer()
                .withFuelType(FuelType.ALL)
                .withSort(TankerkoenigConsumer.SORT.price);
    }

    @Test(expected = IllegalStateException.class)
    public void withFuelTypeIsAllWhileSortIsPrice() {
        new TankerkoenigConsumer()
                .withSort(TankerkoenigConsumer.SORT.price)
                .withFuelType(FuelType.ALL);
    }

    @Test
    @Ignore
    public void consume() throws Exception {
        RangeSearchResponse response = new TankerkoenigConsumer()
                .consume();

        Assert.assertNotNull(response);

        System.out.println(response.toString());
    }

}
