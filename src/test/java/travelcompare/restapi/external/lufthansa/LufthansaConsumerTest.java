package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Test;
import travelcompare.restapi.configuration.UnirestConfiguration;
import travelcompare.restapi.external.lufthansa.response.AuthenticationResponse;
import travelcompare.restapi.external.lufthansa.response.FlightScheduleResponse;
import travelcompare.restapi.external.lufthansa.response.FlightStatusResponse;
import travelcompare.restapi.external.lufthansa.response.NearestAirportResponse;

public class LufthansaConsumerTest {
    @Before
    public void init() {
        UnirestConfiguration.init();
    }

    @Test
    public void testConsumeLufthansaFlightStatus() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        FlightStatusResponse response = consumer.consumeFlightStatus("LH400", "2017-11-08");
        System.out.println(response);
    }

    @Test
    public void testConsumeLufthansaFlightSchedule() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        FlightScheduleResponse response = consumer.consumeFlightSchedule("FRA", "JFK", "2017-11-06");
        System.out.println(response);
    }

    @Test
    public void testConsumeLufthansaNearestAirport() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        NearestAirportResponse response = consumer.consumeNearestAirport("50.212932", "11.943976");
        System.out.println(response);
    }
}
