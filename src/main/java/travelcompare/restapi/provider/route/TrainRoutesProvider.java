package travelcompare.restapi.provider.route;

import com.google.common.collect.Lists;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.joda.time.DateTime;
import travelcompare.restapi.external.bahnprice.BahnPriceConsumer;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TrainRoutesProvider implements RoutesProvider<TrainStation> {

    @Override
    public List<Route> find(TrainStation start, TrainStation destination, Date date) throws InterruptedException, ApiException, IOException, UnirestException {
        GeoApiContext context = GeoApiContextFactory.getBasicGeoApiContext();


        DirectionsApiRequest request = DirectionsApi.newRequest(context)
                .origin(new LatLng(start.getLat(), start.getLon()))
                .destination(new LatLng(destination.getLat(), destination.getLon()))
                .mode(TravelMode.TRANSIT)
                .transitMode(TransitMode.TRAIN)
                .departureTime(new DateTime(date.getTime()).plusDays(1))
                .alternatives(true);

        DirectionsResult requestResult = request.await();

        List<Route> resultList = Lists.newArrayList();

        for (DirectionsRoute googleRoute :
                requestResult.routes) {
            Route route = new Route(Transport.TRAIN);
            resultList.add(route);
            double price = 0;
            for (DirectionsStep step :
                    googleRoute.legs[0].steps) {
                Step routeStep = new Step();
                if (step.travelMode.equals(TravelMode.TRANSIT)) {
                    routeStep.setTransport(Transport.TRAIN);
                    routeStep.setDuration(step.duration.inSeconds/60);
                    routeStep.setStart(new Geo(step.startLocation.lat, step.startLocation.lng));
                    routeStep.setDestination(new TrainStation(step.endLocation.lat, step.endLocation.lng));
                    routeStep.setDistance(step.distance.inMeters);
                    routeStep.setDescription(step.htmlInstructions);
                    route.addStep(routeStep);
                    price+=new BahnPriceConsumer().getBahnPrice(step.distance + "", getType(step.htmlInstructions));
                }
            }
            route.setPrice(price);
        }
        return resultList;
    }

    private String getType(String name) {
        name = name.toLowerCase();
        if (name.matches("commuter.*")) {
            return "RX";
        } else if (name.matches("high speed train.*")) {
            return "ICE";
        } else {
            return "IC";
        }
    }

}
