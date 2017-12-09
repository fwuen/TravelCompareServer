package travelcompare.restapi.provider.perimeter;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainPerimeterSearchProvider implements PerimeterSearchProvider<TrainStation> {

    public List<TrainStation> find(Geo geoPosition, int radius) throws InterruptedException, ApiException, IOException {
        Preconditions.checkArgument(radius <= 50000, "50000m Radius ist das Maximum.");

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

}
