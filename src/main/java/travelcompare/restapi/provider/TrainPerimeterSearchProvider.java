package travelcompare.restapi.provider;


import com.google.common.collect.Lists;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;
import java.util.ArrayList;

public class TrainPerimeterSearchProvider {

    public ArrayList<TrainStation> findTrainstations(Geo geoPosition, int radius) throws InterruptedException, ApiException, IOException {
        LatLng latLngPosition = new LatLng(geoPosition.getLat(), geoPosition.getLon());

        GeoApiContext geoApiContext = GeoApiContextFactory.getBasicGeoApiContext();

        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, latLngPosition)
                .radius(radius)
                .type(PlaceType.TRAIN_STATION);
        PlacesSearchResponse response = nearbySearchRequest.await();

        ArrayList<PlacesSearchResult> responseList = Lists.newArrayList(response.results);
        ArrayList<TrainStation> results = Lists.newArrayList();

        for (PlacesSearchResult r :
                responseList) {
            TrainStation trainStation = new TrainStation(r.geometry.location.lat, r.geometry.location.lng)
                    .setName(r.name);
            results.add(trainStation);
        }
        return results;
    }

    public ArrayList<TrainStation> findTrainstations(Geo geoPosition) throws InterruptedException, ApiException, IOException {
        LatLng latLngPosition = new LatLng(geoPosition.getLat(), geoPosition.getLon());

        GeoApiContext geoApiContext = GeoApiContextFactory.getBasicGeoApiContext();

        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, latLngPosition)
                .type(PlaceType.TRAIN_STATION)
                .rankby(RankBy.DISTANCE);
        PlacesSearchResponse response = nearbySearchRequest.await();

        ArrayList<PlacesSearchResult> responseList = Lists.newArrayList(response.results);
        ArrayList<TrainStation> results = Lists.newArrayList();

        for (PlacesSearchResult r :
                responseList) {
            TrainStation trainStation = new TrainStation(r.geometry.location.lat, r.geometry.location.lng)
                    .setName(r.name);
            results.add(trainStation);
        }
        return results;
    }
}
