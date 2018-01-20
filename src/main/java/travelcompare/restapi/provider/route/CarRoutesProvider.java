package travelcompare.restapi.provider.route;

import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.tankerkoenig.TankerkoenigConsumer;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
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

public class CarRoutesProvider implements RoutesProvider<Geo> {

    /**
     * Provider zum Erhalten der Autorouten
     */
    private ViaMichelinConsumer consumer;

    /**
     * Konstruktor der CarRoutesProvider
     */
    public CarRoutesProvider() {
        consumer = new ViaMichelinConsumer();
    }

    /**
     * Allgemeine Suche, wenn kein Kraftstoff angegeben wird
     *
     * @param start       Start-Koordinaten
     * @param destination Ziel-Koordinaten
     * @param date        Tag
     * @return List<Route>
     */
    @Override
    public List<Route> find(Geo start, Geo destination, Date date) {
        return findWithFuelType(start, destination, date, FuelType.ALL);
    }

    /**
     * Suche nach einer Autoroute mit Angabe des Kraftstoffes
     *
     * @param start       Start-Koordinaten
     * @param destination Ziel-Koordinaten
     * @param date        Tag
     * @param fuelType    Art des Kraftstoffes
     * @return List<Route>
     */
    public List<Route> findWithFuelType(Geo start, Geo destination, Date date, FuelType fuelType) {
        if (start == null || destination == null || date == null) {
            return null;
        }
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

    /**
     * Suchen der günstigsten Route
     *
     * @param start       Start-Koordinaten
     * @param destination Ziel-Koordinaten
     * @param date        Tag
     * @param fuelType    Art des Kraftstoffes
     * @return List<Route>
     */
    private Route findCheapest(Geo start, Geo destination, Date date, FuelType fuelType) {
        return findRoute(4, start, destination, date, fuelType);

    }

    /**
     * Suchen der schnellsten Route
     *
     * @param start       Start-Koordinaten
     * @param destination Ziel-Koordinaten
     * @param date        Tag
     * @param fuelType    Art des Kraftstoffes
     * @return List<Route>
     */
    private Route findFastest(Geo start, Geo destination, Date date, FuelType fuelType) {
        return findRoute(1, start, destination, date, fuelType);

    }

    /**
     * Ansprechen der Tankerkönig Api für Spritpreise und der Via Michelin Api um eine Autoroute zu erhalten
     *
     * @param itit        Art wie Michelin die Abfrage behandeln soll(schnellste oder günstigste Route)
     * @param start       Start-Koordinaten
     * @param destination Ziel-Koordinaten
     * @param date        Tag
     * @param fuelType    Art des Kraftstoffes
     * @return List<Route>
     */
    private Route findRoute(int itit, Geo start, Geo destination, Date date, FuelType fuelType) {
        Route route = new Route(Transport.CAR);

        TankerkoenigConsumer consumer = new TankerkoenigConsumer();
        consumer.withFuelType(fuelType);
        if (!FuelType.ALL.equals(fuelType)) {
            consumer.withSort(TankerkoenigConsumer.SORT.price);
        }
        double fuelCost = 1.20;
        try {
            RangeSearchResponse rangeSearchResponse = consumer.consume();
            if (rangeSearchResponse.isOk()) {
                Station bestPrice = rangeSearchResponse.getStations().get(0);
                fuelCost = bestPrice.getPrice();
            }
        } catch (Exception e) {
            // Ignorieren und Standardwert nehmen
        }

        RouteResponse response;
        try {
            response = new ViaMichelinConsumer().getRoute(start.getLon(), start.getLat(), destination.getLon(), destination.getLat(), itit, fuelCost, date);
            if (response == null
                    || response.getIti() == null
                    || response.getIti().getRoadSheet() == null
                    || response.getIti().getRoadSheet().getRoadSheetStep() == null
                    || response.getIti().getHeader() == null) {
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
            step.setDescription(roadSheetStep.getInstructions());
            route.addStep(step);
        }
        route.setPrice(response.getIti().getHeader().getSummaries().getConsumption());
        return route;
    }
}

