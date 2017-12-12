package travelcompare.restapi.provider.way;

import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.viamichelin.ViaMichelinConsumer;
import travelcompare.restapi.external.viamichelin.model.RoadSheetStep;
import travelcompare.restapi.external.viamichelin.model.RouteResponse;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Transport;
import travelcompare.restapi.provider.model.Way;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarWaysProvider implements WaysProvider<Geo> {
    /**
     * Provider zum Erhalten der Autorouten
     */
    private ViaMichelinConsumer consumer;

    /**
     * Konstruktor der CarWaysProvider
     */
    public CarWaysProvider() {
        consumer = new ViaMichelinConsumer();
    }

    /**
     * @param start       Geo
     * @param destination Geo
     * @param date        Date
     * @return List<Way>
     */
    @Override
    public List<Way> find(Geo start, Geo destination, Date date) {
        Way way = new Way();

        // TODO den Wert über Tankerkönig holen
        double fuelCost = 0.0;
        RouteResponse response;
        try {
            response = consumer.getRoute(start.getLon(), start.getLat(), destination.getLon(), destination.getLat(),  fuelCost);
            if (response == null || response.getIti() == null || response.getIti().getRoadSheet() == null || response.getIti().getRoadSheet().getRoadSheetStep() == null || response.getIti().getHeader() == null) {
                return null; // TODO Was nun?
            }
        } catch (UnirestException exception) {
            return null;
        }
        Geo coordinates = start;
        for (RoadSheetStep roadSheetStep : response.getIti().getRoadSheet().getRoadSheetStep()) {
            Route route = new Route();
            route.setStart(coordinates);
            // Zielkoordinaten ablegen
            coordinates = new Geo(roadSheetStep.getCoords().getLat_coordinate(), roadSheetStep.getCoords().getLong_coordinate());
            route.setDestination(coordinates);
            route.setTransport(Transport.CAR);
            route.setDuration(roadSheetStep.getDuration());
            //   route.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
            way.getRoutes().add(route);
        }
        way.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
        List<Way> ways = new ArrayList<>();
        ways.add(way);
        return ways;
    }

}
