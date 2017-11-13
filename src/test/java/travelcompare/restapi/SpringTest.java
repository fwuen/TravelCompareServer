package travelcompare.restapi;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import travelcompare.restapi.configuration.UnirestConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public abstract class SpringTest {

    @Autowired
    protected MockMvc mockMvc;

    @BeforeClass
    public static void init() {
        UnirestConfiguration.init();
    }

}
