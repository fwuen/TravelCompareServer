package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Test;
import travelcompare.restapi.configuration.UnirestConfiguration;
import travelcompare.restapi.external.lufthansa.response.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LufthansaConsumerTest {
    @Before
    public void init() {
        UnirestConfiguration.init();
    }

    @Test
    public void testConsumeLufthansaFlightStatus() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        FlightStatusResponse response = consumer.consumeFlightStatus("LH400", today);
        System.out.println(response);
    }

    @Test
    public void testConsumeLufthansaFlightSchedule() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        String today = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        FlightScheduleResponse response = consumer.consumeFlightSchedule("FRA", "JFK", today);
        System.out.println(response);
    }

    @Test
    public void testConsumeLufthansaNearestAirport() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        NearestAirportResponse response = consumer.consumeNearestAirport("50.212932", "11.943976");
        System.out.println(response);
    }
    
    @Test
    public void testConsumeLufthansaAllFares() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 6);
        Date todayPlusOneYear = c.getTime();
        String date = new SimpleDateFormat("YYYY-MM-dd").format(todayPlusOneYear);
        AllFaresResponse response = consumer.consumeAllFares("EW", "DUS", "TXL", date);
        System.out.println(response);
    }
    
    @Test
    public void testConsumeLufthansaLowestFares() throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 6);
        Date todayPlusOneYear = c.getTime();
        String date = new SimpleDateFormat("YYYY-MM-dd").format(todayPlusOneYear);
        LowestFaresResponse response = consumer.consumeLowestFares("EW", "DUS", "TXL", date);
        System.out.println(response);
    }
}
