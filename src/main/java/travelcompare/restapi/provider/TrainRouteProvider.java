package travelcompare.restapi.provider;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.Trainstation;

import java.io.IOException;

public class TrainRouteProvider implements RouteProvider {
    @Override
    public Route getRoute(Geo start, Geo destination) throws InterruptedException, ApiException, IOException {
        LatLng startLatLng = new LatLng(start.getLat(), start.getLon());
        LatLng destLatLng = new LatLng(destination.getLat(), destination.getLon());

        GeoApiContext geoApiContext = GeoApiContextFactory.getBasicGeoApiContext();

        DirectionsApiRequest request = DirectionsApi.newRequest(geoApiContext);
        request.origin(startLatLng)
                .destination(destLatLng)
                .mode(TravelMode.TRANSIT)
                .transitMode(TransitMode.TRAIN);

        DirectionsResult requestResult = request.await();
        DirectionsRoute route = requestResult.routes[0];

        Trainstation trainstationStart = new Trainstation(start.getLat(), start.getLon());

        return new Route().setStart(start)
                .setDestination(destination)
                .setDuration((route.legs[0].duration.inSeconds) / 60)
                .setRouteRepresentation(route)
                .setPrice(route.fare.value.intValue());
    }
}
