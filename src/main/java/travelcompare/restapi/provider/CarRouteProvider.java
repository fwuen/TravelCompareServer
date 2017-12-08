package travelcompare.restapi.provider;

import com.google.maps.model.DirectionsRoute;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.viamichelin.ViaMichelinConsumer;
import travelcompare.restapi.external.viamichelin.model.RoadSheetStep;
import travelcompare.restapi.external.viamichelin.model.RouteResponse;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Transport;

import java.util.ArrayList;

import java.util.Date;

public class CarRouteProvider implements RouteProvider {

    @Override
    public Route getRoute(Geo start, Geo destination) throws UnirestException {
        ViaMichelinConsumer consumer = new ViaMichelinConsumer();
        Route route = new Route();

        // TODO den Wert über Tankerkönig holen
        double fuelCost = 0.0;
        RouteResponse response = consumer.getRoute(start.getLon(), start.getLat(), destination.getLon(), destination.getLat(), 3, fuelCost);
        if (response == null || response.getIti() == null || response.getIti().getRoadSheet() == null || response.getIti().getRoadSheet().getRoadSheetStep() == null || response.getIti().getHeader() == null) {
            return null; // TODO Was nun?
        }
        route.setDestination(destination);
        route.setStart(start);
        route.setTransport(Transport.CAR);
        route.setDuration(response.getIti().getHeader().getSummaries().getTotalTime().longValue());
        route.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
        // Lat/Long Koordinaten der einzelnen Teilrouten
        ArrayList<Geo> steps = new ArrayList<>();
        for (RoadSheetStep roadSheetStep : response.getIti().getRoadSheet().getRoadSheetStep()) {
            steps.add(new Geo(roadSheetStep.getCoords().getLat_coordinate(), roadSheetStep.getCoords().getLong_coordinate()));
        }
        // TODO Was genau wird von DirectionsRoute gebraucht?
        route.setRouteRepresentation(null);

        return route;
    }

    @Override
    public Route getRouteOnDate(Geo start, Geo destination, Date travelDate) throws Exception {
        return null;
    }
}
