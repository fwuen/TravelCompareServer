package travelcompare.restapi.provider.way;

import com.google.common.collect.Lists;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.joda.time.DateTime;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TrainWaysProvider implements WaysProvider<TrainStation> {

    @Override
    public List<Way> find(TrainStation start, TrainStation destination, Date date) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = GeoApiContextFactory.getBasicGeoApiContext();


        DirectionsApiRequest request = DirectionsApi.newRequest(context)
                .origin(new LatLng(start.getLat(), start.getLon()))
                .destination(new LatLng(destination.getLat(), destination.getLon()))
                .mode(TravelMode.TRANSIT)
                .transitMode(TransitMode.TRAIN)
                .departureTime(new DateTime(date.getTime()).plusDays(1))
                .alternatives(true);

        DirectionsResult requestResult = request.await();

        List<Way> resultList = Lists.newArrayList();

        for (DirectionsRoute route :
                requestResult.routes) {
            Way way = new Way();
            resultList.add(way);
            for (DirectionsStep step :
                    route.legs[0].steps) {
                Route wayRoute = new Route();
                if (step.travelMode.equals(TravelMode.TRANSIT)) {
                    wayRoute.setTransport(Transport.TRAIN);
                    wayRoute.setDuration(step.duration.inSeconds/60);
                    wayRoute.setStart(new Geo(step.startLocation.lat, step.startLocation.lng));
                    wayRoute.setDestination(new Geo(step.endLocation.lat, step.endLocation.lng));
                    way.getRoutes().add(wayRoute);
                }

            }
        }

        return resultList;
    }

}
