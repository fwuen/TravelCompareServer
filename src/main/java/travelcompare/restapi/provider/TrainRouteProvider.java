package travelcompare.restapi.provider;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Route;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;
import java.util.Date;

public class TrainRouteProvider implements RouteProvider<TrainStation> {

    @Override
    public Route getRoute(TrainStation start, TrainStation destination) throws InterruptedException, ApiException, IOException {
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
        
        Route returnRoute = new Route();

        returnRoute.setStart(start)
                .setStart(start)
                .setDestination(destination)
                .setDuration((route.legs[0].duration.inSeconds) / 60)
                .setRouteRepresentation(route);

        if (route.fare != null) {
            returnRoute.setPrice(route.fare.value.doubleValue());
        }

        return returnRoute;
    }
    
    @Override
    public Route getRouteOnDate(TrainStation start, TrainStation destination, Date travelDate) throws Exception {
        return null;
    }
}
