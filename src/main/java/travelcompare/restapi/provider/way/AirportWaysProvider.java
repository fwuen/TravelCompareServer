package travelcompare.restapi.provider.way;

import com.google.common.collect.Lists;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.lufthansa.LufthansaConsumer;
import travelcompare.restapi.external.lufthansa.model.AirlineOffer;
import travelcompare.restapi.external.lufthansa.response.AllFaresResponse;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Way;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AirportWaysProvider implements WaysProvider<Airport> {

    @Override
    public List<Way> find(Airport start, Airport destination, Date date) {
        AllFaresResponse allFares;
        try {
            allFares = getFaresFromStartToDestinationOnDate(start, destination, date);
        } catch (UnirestException e) {
            return null;
        }

        List<Way> ways = getWaysFromFares(allFares);

        return null;
    }

    private AllFaresResponse getFaresFromStartToDestinationOnDate(Airport start, Airport destination, Date date) throws UnirestException {
        LufthansaConsumer consumer = new LufthansaConsumer();
        return consumer.consumeAllFares("EW", start.getIdentifier(), destination.getIdentifier(), date.toString());
    }

    private List<Way> getWaysFromFares(AllFaresResponse allFares) {
        List<Way> ways = Lists.newArrayList();

        return null;
    }

    private List<Route> getRoutesFromFares(AllFaresResponse allFares) {
        List<Route> routes = Lists.newArrayList();
        for(AirlineOffer offer : allFares.getFaresResponse().getAirShoppingResponse().getOffersGroup().getAirlineOffers().getAirlineOfferList()) {

        }
        return null;
    }

}
