package travelcompare.restapi.provider.way;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.lufthansa.LufthansaConsumer;
import travelcompare.restapi.external.lufthansa.model.AirlineOffer;
import travelcompare.restapi.external.lufthansa.model.FlightSegment;
import travelcompare.restapi.external.lufthansa.model.FlightSegmentReference;
import travelcompare.restapi.external.lufthansa.response.AirportsResponse;
import travelcompare.restapi.external.lufthansa.response.AllFaresResponse;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Transport;
import travelcompare.restapi.provider.model.Way;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AirportWaysProvider implements WaysProvider<Airport> {
    private LufthansaConsumer consumer;
    private String startCodeOfSegmentBefore = null;
    private String destCodeOfSegmentBefore = null;
    private Airport startAirportBefore = null;
    private Airport destAirportBefore = null;
    
    public AirportWaysProvider() {
        consumer = new LufthansaConsumer();
    }

    @Override
    public List<Way> find(Airport start, Airport destination, Date date) {
        AllFaresResponse allFares;
        try {
            allFares = getFaresFromStartToDestinationOnDate(start, destination, date);
        } catch (UnirestException e) {
            return Lists.newArrayList();
        }
    
        List<Way> ways = Lists.newArrayList();
        try {
            ways = buildWaysFromResponse(allFares);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return ways;
    }

    private AllFaresResponse getFaresFromStartToDestinationOnDate(Airport start, Airport destination, Date date) throws UnirestException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String formatted_date = dateFormat.format(date);

        return consumer.consumeAllFares("EW", start.getIdentifier(), destination.getIdentifier(), formatted_date);
    }
    
    private List<Way> buildWaysFromResponse(AllFaresResponse response) throws UnirestException, ParseException {
        List<Way> ways = Lists.newArrayList();
        
        for(AirlineOffer offer : response.getFaresResponse().getAirShoppingResponse().getOffersGroup().getAirlineOffers().getAirlineOfferList()) {
            Way newWayForOffer = addRoutesToWay(offer.getTotalPrice().getDetailCurrencyPrice().getTotal().getValue(), offer.getPricedOffer().getAssociations().getApplicableFlight().getFlightSegmentReference(), response.getFaresResponse().getAirShoppingResponse().getDataLists().getFlightSegmentList().getFlightSegments());
            ways.add(newWayForOffer);
        }
        
        return ways;
    }
    
    private Way addRoutesToWay(String price, List<FlightSegmentReference> references, List<FlightSegment> flightSegments) throws UnirestException, ParseException {
        Way way = new Way();
        
        for(FlightSegmentReference reference : references) {
            for(FlightSegment segment : flightSegments) {
                if(reference.getRef().equals(segment.getSegmentKey())) {
                    Airport start;
                    if(startCodeOfSegmentBefore == null || !startCodeOfSegmentBefore.equals(segment.getDeparture().getAirportCode())) {
                        start = getAirportFromIdentifier(segment.getDeparture().getAirportCode());
                    } else {
                        start = new Airport(startAirportBefore.getLat(), startAirportBefore.getLon());
                    }
                    Airport destination;
                    if(destCodeOfSegmentBefore == null || !destCodeOfSegmentBefore.equals(segment.getArrival().getAirportCode())) {
                        destination = getAirportFromIdentifier(segment.getArrival().getAirportCode());
                    } else {
                        destination = new Airport(destAirportBefore.getLat(), destAirportBefore.getLon());
                    }
                    Route route = new Route();
                    route.setStart(start);
                    route.setDestination(destination);
                    route.setTransport(Transport.AIRCRAFT);
                    route.setDuration(getTimeDifferenceInMinutesFromDates(convertStringToDate(segment.getDeparture().getTime()), convertStringToDate(segment.getArrival().getTime())));
                    way.addRoute(route);
                    
                    startCodeOfSegmentBefore = segment.getDeparture().getAirportCode();
                    startAirportBefore = start;
                    destCodeOfSegmentBefore = segment.getArrival().getAirportCode();
                    destAirportBefore = destination;
                }
            }
        }
        way.setPrice(Double.parseDouble(price));
        
        return way;
    }
    
    private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.parse(dateString);
    }
    
    private long getTimeDifferenceInMinutesFromDates(Date start, Date end) {
        Calendar c = Calendar.getInstance();
        long diff = end.getTime() - start.getTime();
        
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }
    
    private Airport getAirportFromIdentifier(String identifier) throws UnirestException {
        AirportsResponse response = consumer.consumeAirports(identifier);
        Airport newAirport = new Airport(response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLatitude(), response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLongitude());
        newAirport.setIdentifier(identifier);
        return newAirport;
        
    }

}
