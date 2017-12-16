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
        if (start == null || destination == null || date == null) {
            return null;
        }
        List<Way> ways = new ArrayList<>();
        Way way = findCheapest(start, destination, date);
        if (way != null) {
            ways.add(way);
        }
        way = findFastest(start, destination, date);
        if (way != null) {
            ways.add(way);
        }

        return ways;
    }

    private Way findCheapest(Geo start, Geo destination, Date date) {
        return findRoute(4, start, destination, date);
    }

    private Way findFastest(Geo start, Geo destination, Date date) {
        return findRoute(1, start, destination, date);
    }

    private Way findRoute(int itit, Geo start, Geo destination, Date date) {
        Way way = new Way();

        // TODO den Wert über Tankerkönig holen
        double fuelCost = 1.20;
        RouteResponse response;
        try {
            response = new ViaMichelinConsumer().getRoute(start.getLon(), start.getLat(), destination.getLon(), destination.getLat(), itit, fuelCost, new Date());
            if (response == null || response.getIti() == null || response.getIti().getRoadSheet() == null || response.getIti().getRoadSheet().getRoadSheetStep() == null || response.getIti().getHeader() == null) {
                return null;
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
            way.getRoutes().add(route);
        }
        way.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
        return way;
    }
}
