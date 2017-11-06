package travelcompare.restapi;

import org.junit.Before;
import travelcompare.restapi.configuration.UnirestConfiguration;

public class SpringTest {

    @Before
    public void init() {
        UnirestConfiguration.init();
    }

}
