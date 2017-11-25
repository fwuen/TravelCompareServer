package travelcompare.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import travelcompare.restapi.external.configuration.UnirestConfiguration;

@SpringBootApplication
public class SpringStarter {

    public static void main(String[] args) {
        UnirestConfiguration.init();

        SpringApplication.run(SpringStarter.class);
    }

}