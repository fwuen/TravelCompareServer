package travelcompare.restapi.provider.way;

import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.tankerkoenig.TankerkoenigConsumer;
import travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;
import travelcompare.restapi.external.tankerkoenig.response.Station;
import travelcompare.restapi.external.viamichelin.ViaMichelinConsumer;
import travelcompare.restapi.external.viamichelin.model.RoadSheetStep;
import travelcompare.restapi.external.viamichelin.model.RouteResponse;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Step;
import travelcompare.restapi.provider.model.Transport;

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
     * @param start
     * @param destination
     * @param date
     * @return
     */
    @Override
    public List<Route> find(Geo start, Geo destination, Date date) {
        return null;
    }

    /**
     * @param start       Geo
     * @param destination Geo
     * @param date        Date
     * @return List<Route>
     */
    public List<Route> findWithFuelType(Geo start, Geo destination, Date date, FUEL_TYPE fuelType) {
        if (start == null || destination == null || date == null) {
            return null;
        }
        // TODO Hier noch den Spritpreis holen und mit einbeziehen

        List<Route> routes = new ArrayList<>();
        Route route = findCheapest(start, destination, date, fuelType);
        if (route != null) {
            routes.add(route);
        }
        route = findFastest(start, destination, date, fuelType);
        if (route != null) {
            routes.add(route);
        }

        return routes;
    }

    private Route findCheapest(Geo start, Geo destination, Date date, FUEL_TYPE fuelType) {
        try {
            return findRoute(4, start, destination, date, fuelType);
        } catch (UnirestException e) {
            return null;
        }
    }

    private Route findFastest(Geo start, Geo destination, Date date, FUEL_TYPE fuelType) {
        try {
            return findRoute(1, start, destination, date, fuelType);
        } catch (UnirestException e) {
            return null;
        }
    }

    private Route findRoute(int itit, Geo start, Geo destination, Date date, FUEL_TYPE fuelType) throws UnirestException {
        Route route = new Route();

        TankerkoenigConsumer consumer = new TankerkoenigConsumer();
        consumer.withFuelType(fuelType);
        consumer.withSort(TankerkoenigConsumer.SORT.price);
        RangeSearchResponse rangeSearchResponse = null;
        double fuelCost = 1.20;
        try {
            rangeSearchResponse = consumer.consume();
            if (rangeSearchResponse.isOk()) {
                Station bestPrice = rangeSearchResponse.getStations().get(0);
                fuelCost = bestPrice.getPrice();
            }
        }catch (Exception e){
            // Ignorieren und Standardwert nehmen
        }

        RouteResponse response;
        try {
            response = new ViaMichelinConsumer().getRoute(start.getLon(), start.getLat(), destination.getLon(), destination.getLat(), itit, fuelCost, date);
            if (response == null || response.getIti() == null || response.getIti().getRoadSheet() == null || response.getIti().getRoadSheet().getRoadSheetStep() == null || response.getIti().getHeader() == null) {
                return null;
            }
        } catch (UnirestException exception) {
            return null;
        }
        Geo coordinates = start;
        for (RoadSheetStep roadSheetStep : response.getIti().getRoadSheet().getRoadSheetStep()) {
            Step step = new Step();
            step.setStart(coordinates);
            // Zielkoordinaten ablegen
            coordinates = new Geo(roadSheetStep.getCoords().getLat_coordinate(), roadSheetStep.getCoords().getLong_coordinate());
            step.setDestination(coordinates);
            step.setTransport(Transport.CAR);
            step.setDuration(roadSheetStep.getDuration());
            step.setDistance(roadSheetStep.getDistance());
            route.getSteps().add(step);
        }
        route.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
        return route;
    }
}
