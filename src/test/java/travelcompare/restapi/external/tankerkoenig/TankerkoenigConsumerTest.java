package travelcompare.restapi.external.tankerkoenig;

import org.junit.Before;
import org.junit.Test;
import travelcompare.restapi.configuration.UnirestConfiguration;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;

public class TankerkoenigConsumerTest {
    @Before
    public void init() {
        UnirestConfiguration.init();
    }

    @Test
    public void test() throws Exception {
        TankerkoenigConsumer consumer = new TankerkoenigConsumer();
        RangeSearchResponse response = consumer.consume();

        System.out.println(response.toString());
    }
}
