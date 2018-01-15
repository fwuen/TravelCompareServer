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
import travelcompare.restapi.provider.model.Step;
import travelcompare.restapi.provider.model.Transport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AirportWaysProvider implements WaysProvider<Airport> {
    private LufthansaConsumer consumer;
    
    public AirportWaysProvider() {
        consumer = new LufthansaConsumer();
    }

    @Override
    public List<Route> find(Airport start, Airport destination, Date date) {
        AllFaresResponse allFares;
        try {
            allFares = getFaresFromStartToDestinationOnDate(start, destination, date);
        } catch (UnirestException e) {
            return Lists.newArrayList();
        }
    
        List<Route> routes = Lists.newArrayList();
        try {
            routes = buildWaysFromResponse(allFares);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return routes;
    }

    private AllFaresResponse getFaresFromStartToDestinationOnDate(Airport start, Airport destination, Date date) throws UnirestException {
        consumer = new LufthansaConsumer();
        return consumer.consumeAllFares("EW", start.getIdentifier(), destination.getIdentifier(), date.toString());
    }
    
    private List<Route> buildWaysFromResponse(AllFaresResponse response) throws UnirestException, ParseException {
        List<Route> routes = Lists.newArrayList();
        
        for(AirlineOffer offer : response.getFaresResponse().getAirShoppingResponse().getOffersGroup().getAirlineOffers().getAirlineOfferList()) {
            Route newRouteForOffer = addRoutesToWay(offer.getTotalPrice().getDetailCurrencyPrice().getTotal().getValue(), offer.getPricedOffer().getAssociations().getApplicableFlight().getFlightSegmentReference(), response.getFaresResponse().getAirShoppingResponse().getDataLists().getFlightSegmentList().getFlightSegments());
            routes.add(newRouteForOffer);
        }
        
        return routes;
    }
    
    private Route addRoutesToWay(String price, List<FlightSegmentReference> references, List<FlightSegment> flightSegments) throws UnirestException, ParseException {
        Route route = new Route();
        for(FlightSegmentReference reference : references) {
            for(FlightSegment segment : flightSegments) {
                if(reference.getRef().equals(segment.getSegmentKey())) {
                    Airport start = getAirportFromIdentifier(segment.getDeparture().getAirportCode());
                    Airport destination = getAirportFromIdentifier(segment.getArrival().getAirportCode());
                    Step step = new Step();
                    step.setStart(start);
                    step.setDestination(destination);
                    step.setTransport(Transport.AIRCRAFT);
                    step.setDuration(getTimeDifferenceInMinutesFromDates(convertStringToDate(segment.getDeparture().getTime()), convertStringToDate(segment.getArrival().getTime())));
                    route.getSteps().add(step);
                }
            }
        }
        route.setPrice(Double.parseDouble(price));
        
        return route;
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
