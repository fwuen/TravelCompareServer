package travelcompare.restapi.provider.route;

import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.lufthansa.LufthansaConsumer;
import travelcompare.restapi.external.lufthansa.model.FlightSegment;
import travelcompare.restapi.external.lufthansa.model.FlightSegmentReference;
import travelcompare.restapi.external.lufthansa.response.LowestFaresResponse;
import travelcompare.restapi.external.lufthansa.response.NearestAirportResponse;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Route;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static travelcompare.restapi.provider.model.Transport.AIRCRAFT;

public class AircraftRouteProvider implements RouteProvider<Airport> {
    private String cataloguesForFlightSearch = "EW";

    @Override
    public Route getRoute(Airport start, Airport destination) {
        return getRouteOnDate(start, destination, new Date());
    }
    
    @Override
    public Route getRouteOnDate(Airport start, Airport destination, Date travelDate) {
        LufthansaConsumer consumer = new LufthansaConsumer();
        LowestFaresResponse lowestFaresResponse;
        String dateString = new SimpleDateFormat("YYYY-MM-dd").format(travelDate);
        List<FlightSegment> flightSegments;
    
        try {
            NearestAirportResponse startNearestAirportResponse = consumer.consumeNearestAirport(String.valueOf(start.getLat()), String.valueOf(start.getLon()));
            NearestAirportResponse destinationNearestAirportsResponse = consumer.consumeNearestAirport(String.valueOf(destination.getLat()), String.valueOf(destination.getLon()));
    
            String startAirport = getAirportCodeFromResponse(startNearestAirportResponse);
            String destinationAirport = getAirportCodeFromResponse(destinationNearestAirportsResponse);
    
            lowestFaresResponse = new LufthansaConsumer().consumeLowestFares(cataloguesForFlightSearch, startAirport, destinationAirport, dateString);
    
        } catch (UnirestException e) {
            return null;
        }
        
        flightSegments = getFlightSegmentsForFlight(lowestFaresResponse);
        
        double price = getPriceFromResponse(lowestFaresResponse);
        String departureTimeString = getDepartureTimeStringFromResponse(flightSegments);
        String arrivalTimeString = getArrivalTimeStringFromResponse(flightSegments);
        Date departureTime;
        Date arrivalTime;
        
        try {
            departureTime = convertStringToDate(departureTimeString);
            arrivalTime = convertStringToDate(arrivalTimeString);
        } catch(ParseException e) {
            return null;
        }
        long duration = getDifferenceFromDates(departureTime, arrivalTime);
    
        Route aircraftRoute = new Route();
        aircraftRoute.setStart(start);
        aircraftRoute.setDestination(destination);
        aircraftRoute.setDuration(duration);
        aircraftRoute.setPrice(price);
        aircraftRoute.setFlightSegments(flightSegments);
        aircraftRoute.setTransport(AIRCRAFT);
    
        return aircraftRoute;
    }
    
    private List<FlightSegment> getFlightSegmentsForFlight(LowestFaresResponse response) {
        List<FlightSegmentReference> flightSegmentReferences = response.getLowestFaresResponse().
                getAirShoppingResponse().
                getOffersGroup().
                getAirlineOffers().
                getAirlineOfferList().
                get(0).
                getPricedOffer().
                getAssociations().
                getApplicableFlight().
                getFlightSegmentReference();
        
        List<String> flightSegmentReferenceKeys = new ArrayList<>();
        
        for (FlightSegmentReference flightSegmentReference :
             flightSegmentReferences) {
            flightSegmentReferenceKeys.add(flightSegmentReference.getRef());
        }
        
        List<FlightSegment> flightSegments = response.
                getLowestFaresResponse().
                getAirShoppingResponse().
                getDataLists().
                getFlightSegmentList().
                getFlightSegments();
        
        List<FlightSegment> filteredFlightSegments = new ArrayList<>();
        
        for(FlightSegment flightSegment : flightSegments) {
            for(String flightSegmentReferenceKey : flightSegmentReferenceKeys) {
                if(flightSegmentReferenceKey.equals(flightSegment.getSegmentKey())) {
                    filteredFlightSegments.add(flightSegment);
                }
            }
        }

        return filteredFlightSegments;
    }
    
    private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(dateString);

        return date;
    }

    private long getDifferenceFromDates(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();

        return TimeUnit.MILLISECONDS.toMinutes(diff);
        /*Calendar c = Calendar.getInstance();
        c.setTime(date2);
        long date2Millis = c.getTimeInMillis();
        c.setTime(date1);
        long date1Millis = c.getTimeInMillis();
        
        long diff = date2Millis - date1Millis;
        long diffMinutes = diff / 60000;
        return diffMinutes;*/
    }

    private String getAirportCodeFromResponse(NearestAirportResponse response) {
        return response.
                getNearestAirportResource().
                getAirports().
                getAirport().
                get(0).
                getAirportCode();
    }

    private double getPriceFromResponse(LowestFaresResponse response) {
        return Double.parseDouble(response.
                getLowestFaresResponse().
                getAirShoppingResponse().
                getOffersGroup().
                getAirlineOffers().
                getAirlineOfferList().
                get(0).
                getTotalPrice().
                getDetailCurrencyPrice().
                getTotal().
                getValue());
    }

    private String getDepartureTimeStringFromResponse(List<FlightSegment> flightSegments) {
        return flightSegments.
                get(0).
                getDeparture().
                getTime();
    }

    private String getArrivalTimeStringFromResponse(List<FlightSegment> flightSegments) {
        return flightSegments.
                get(flightSegments.size()-1).
                getArrival().
                getTime();
    }
}
