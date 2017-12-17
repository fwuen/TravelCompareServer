package travelcompare.restapi.provider.perimeter;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainPerimeterSearchProvider implements PerimeterSearchProvider<TrainStation> {

    public List<TrainStation> findNearest(Geo geoPosition) throws InterruptedException, ApiException, IOException {
        LatLng latLngPosition = new LatLng(geoPosition.getLat(), geoPosition.getLon());

        GeoApiContext geoApiContext = GeoApiContextFactory.getBasicGeoApiContext();

        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, latLngPosition)
                .rankby(RankBy.DISTANCE)
                .type(PlaceType.TRAIN_STATION);
        PlacesSearchResponse response = nearbySearchRequest.await();

        ArrayList<PlacesSearchResult> responseList = Lists.newArrayList(response.results);
        ArrayList<TrainStation> results = Lists.newArrayList();

        int max = responseList.size() < 5 ? responseList.size() : 5;
        for (int i = 0; i < max; i++) {
            PlacesSearchResult current = responseList.get(i);
            TrainStation trainStation = new TrainStation(current.geometry.location.lat, current.geometry.location.lng)
                    .setName(current.name);
            results.add(trainStation);
        }
        return results;
    }

}
