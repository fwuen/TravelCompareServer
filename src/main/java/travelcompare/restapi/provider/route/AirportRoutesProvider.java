package travelcompare.restapi.provider.route;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AirportRoutesProvider implements RoutesProvider<Airport> {
    private LufthansaConsumer consumer;
    private String startCodeOfSegmentBefore = null;
    private String destCodeOfSegmentBefore = null;
    private Airport startAirportBefore = null;
    private Airport destAirportBefore = null;
    private List<Airport> airports = Lists.newArrayList();
    
    public AirportRoutesProvider() {
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
            routes = buildRoutesFromResponse(allFares);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return routes;
    }
    
    private AllFaresResponse getFaresFromStartToDestinationOnDate(Airport start, Airport destination, Date date) throws UnirestException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String formatted_date = dateFormat.format(date);
        
        return consumer.consumeAllFares("EW", start.getIdentifier(), destination.getIdentifier(), formatted_date);
    }
    
    private List<Route> buildRoutesFromResponse(AllFaresResponse response) throws UnirestException, ParseException {
        List<Route> routes = Lists.newArrayList();
        
        for (AirlineOffer offer : response.getFaresResponse().getAirShoppingResponse().getOffersGroup().getAirlineOffers().getAirlineOfferList()) {
            Route newRouteForOffer = addStepsToRoute(offer.getTotalPrice().getDetailCurrencyPrice().getTotal().getValue(), offer.getPricedOffer().getAssociations().getApplicableFlight().getFlightSegmentReference(), response.getFaresResponse().getAirShoppingResponse().getDataLists().getFlightSegmentList().getFlightSegments());
            routes.add(newRouteForOffer);
        }
        
        return routes;
    }
    
    private Route addStepsToRoute(String price, List<FlightSegmentReference> references, List<FlightSegment> flightSegments) throws UnirestException, ParseException {
        Route route = new Route(Transport.AIRCRAFT);
        
        for (FlightSegmentReference reference : references) {
            for (FlightSegment segment : flightSegments) {
                if (reference.getRef().equals(segment.getSegmentKey())) {
                    String airportCodeDeparture = segment.getDeparture().getAirportCode();
                    String airportCodeDestination = segment.getArrival().getAirportCode();
                    Airport start = null;
                    Airport destination = null;
                    
                    for (Airport airport : airports) {
                        if (airport.getIdentifier().equals(airportCodeDeparture)) {
                            start = new Airport(airport.getLat(), airport.getLon());
                            start.setIdentifier(airport.getIdentifier());
                            start.setName(airport.getName());
                        } else if (airport.getIdentifier().equals(airportCodeDestination)) {
                            destination = new Airport(airport.getLat(), airport.getLon());
                            destination.setIdentifier(airport.getIdentifier());
                            destination.setName(airport.getName());
                        }
                    }
                    
                    if(start == null) {
                        AirportsResponse response = consumer.consumeAirports(segment.getDeparture().getAirportCode());
                        start = new Airport(response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLatitude(), response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLongitude());
                        start.setIdentifier(segment.getDeparture().getAirportCode());
                        start.setName(segment.getDeparture().getAirportCode());
                        airports.add(start);
                    }
                    
                    if(destination == null) {
                        AirportsResponse response = consumer.consumeAirports(segment.getArrival().getAirportCode());
                        destination = new Airport(response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLatitude(), response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLongitude());
                        destination.setIdentifier(segment.getArrival().getAirportCode());
                        destination.setName(segment.getArrival().getAirportCode());
                        airports.add(destination);
                    }
                    /*
                    Airport start;
                    if(startCodeOfSegmentBefore == null || !startCodeOfSegmentBefore.equals(segment.getDeparture().getAirportCode())) {
                        AirportsResponse response = consumer.consumeAirports(segment.getDeparture().getAirportCode());
                        Airport newAirport = new Airport(response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLatitude(), response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLongitude());
                        newAirport.setIdentifier(segment.getDeparture().getAirportCode());
                        newAirport.setName(segment.getDeparture().getAirportCode());
                        start = newAirport;
                    } else {
                        start = new Airport(startAirportBefore.getLat(), startAirportBefore.getLon());
                        start.setName(startAirportBefore.getName());
                        start.setIdentifier(startAirportBefore.getIdentifier());
                    }
                    Airport destination;
                    if(destCodeOfSegmentBefore == null || !destCodeOfSegmentBefore.equals(segment.getArrival().getAirportCode())) {
                        AirportsResponse response = consumer.consumeAirports(segment.getDeparture().getAirportCode());
                        Airport newAirport = new Airport(response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLatitude(), response.getAirportResource().getAirports().getAirport().get(0).getPosition().getCoordinate().getLongitude());
                        newAirport.setIdentifier(segment.getArrival().getAirportCode());
                        newAirport.setName(segment.getArrival().getAirportCode());
                        destination = newAirport;
                    } else {
                        destination = new Airport(destAirportBefore.getLat(), destAirportBefore.getLon());
                        destination.setName(destAirportBefore.getName());
                        destination.setIdentifier(destAirportBefore.getIdentifier());
                    }*/
                    Step step = new Step();
                    step.setStart(start);
                    step.setDestination(destination);
                    step.setTransport(Transport.AIRCRAFT);
                    step.setDuration(getTimeDifferenceInMinutesFromDates(convertStringToDate(segment.getDeparture().getTime()), convertStringToDate(segment.getArrival().getTime())));
                    step.setDescription("Flug von " + start.getIdentifier() + " nach " + destination.getIdentifier() + " am " + segment.getDeparture().getDate() + " um " + segment.getDeparture().getTime());
                    route.addStep(step);
                    
                    /*
                    startCodeOfSegmentBefore = segment.getDeparture().getAirportCode();
                    startAirportBefore = start;
                    destCodeOfSegmentBefore = segment.getArrival().getAirportCode();
                    destAirportBefore = destination;*/
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
    
    private double getTimeDifferenceInMinutesFromDates(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        
        return TimeUnit.MILLISECONDS.toSeconds(diff) / 60;
    }
    
}
