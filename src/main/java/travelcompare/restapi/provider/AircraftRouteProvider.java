package travelcompare.restapi.provider;

import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.lufthansa.LufthansaConsumer;
import travelcompare.restapi.external.lufthansa.response.LowestFaresResponse;
import travelcompare.restapi.external.lufthansa.response.NearestAirportResponse;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static travelcompare.restapi.provider.model.Transport.AIRCRAFT;

public class AircraftRouteProvider implements RouteProvider {
    private String cataloguesForFlightSearch = "EW";

    @Override
    public Route getRoute(Geo start, Geo destination) throws UnirestException, ParseException {
        Date travelDate = new Date();

        NearestAirportResponse startNearestAirportResponse = null;
        NearestAirportResponse destinationNearestAirportsResponse = null;
        LowestFaresResponse lowestFaresResponse = null;

        startNearestAirportResponse = new LufthansaConsumer().consumeNearestAirport(String.valueOf(start.getLat()), String.valueOf(start.getLon()));
        destinationNearestAirportsResponse = new LufthansaConsumer().consumeNearestAirport(String.valueOf(start.getLat()), String.valueOf(start.getLon()));


        String startAirport = getAirportCodeFromResponse(startNearestAirportResponse);
        String destinationAirport = getAirportCodeFromResponse(destinationNearestAirportsResponse);

        lowestFaresResponse = new LufthansaConsumer().consumeLowestFares(cataloguesForFlightSearch, startAirport, destinationAirport, travelDate.toString());

        double price = getPriceFromResponse(lowestFaresResponse);
        String departureTimeString = getDepartureTimeStringFromResponse(lowestFaresResponse);
        String arrivalTimeString = getArrivalTimeStringFromResponse(lowestFaresResponse);
        Date departureTime = convertStringToDate(departureTimeString);
        Date arrivalTime = convertStringToDate(arrivalTimeString);
        long duration = getDifferenceFromDates(departureTime, arrivalTime);

        Route aircraftRoute = new Route();
        aircraftRoute.setStart(start);
        aircraftRoute.setDestination(destination);
        aircraftRoute.setDuration(duration);
        aircraftRoute.setPrice(price);
        aircraftRoute.setTransport(AIRCRAFT);

        return aircraftRoute;
    }

    private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateString);;

        return date;
    }

    private long getDifferenceFromDates(Date date1, Date date2) {
        long diff = date1.getTime() - date1.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return diffMinutes;
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

    private String getDepartureTimeStringFromResponse(LowestFaresResponse response) {
        return response.
                getLowestFaresResponse().
                getAirShoppingResponse().
                getDataLists().
                getFlightSegmentList().
                getFlightSegmentList().
                get(0).
                getDeparture().
                getTime();
    }

    private String getArrivalTimeStringFromResponse(LowestFaresResponse response) {
        return response.
                getLowestFaresResponse().
                getAirShoppingResponse().
                getDataLists().
                getFlightSegmentList().
                getFlightSegmentList().
                get(0).
                getArrival().
                getTime();
    }
}
