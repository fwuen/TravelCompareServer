package travelcompare.restapi.provider;

import org.junit.Test;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.route.AircraftRouteProvider;

import java.util.Calendar;
import java.util.Date;

public class AircraftRouteProviderTest extends SpringTest {
    @Test
    public void testGetRoute() {
        AircraftRouteProvider provider = new AircraftRouteProvider();
        Airport start = new Airport(51.281111, 6.752777);
        Airport destination = new Airport(52.560277, 13.295555);
        Route route = null;
        try {
            route = provider.getRoute(start, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(route != null) {
            System.out.println(route.getStart());
            System.out.println(route.getDestination());
            System.out.println(route.getDuration());
            System.out.println(route.getPrice());
            System.out.println(route.getFlightSegments().size());
        }
    }
    
    @Test
    public void testGetRouteOnDate() {
        AircraftRouteProvider provider = new AircraftRouteProvider();
        //Geocoordinates of DUS
        Airport start = new Airport(51.281111, 6.752777);
        //Geocoordinates of TXL
        Airport destination = new Airport(52.560277, 13.295555);
        Route route = null;
        
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 6);
        Date travelDate = c.getTime();
        
        try {
            route = provider.getRouteOnDate(start, destination, travelDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(route != null) {
            System.out.println(route.getStart());
            System.out.println(route.getDestination());
            System.out.println(route.getPrice());
            System.out.println(route.getDuration());
            System.out.println(route.getFlightSegments().size());
        }
    }
}
