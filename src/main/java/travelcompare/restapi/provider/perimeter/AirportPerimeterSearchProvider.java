package travelcompare.restapi.provider.perimeter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import travelcompare.restapi.external.google.GeoApiContextFactory;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.TrainStation;

import java.util.ArrayList;
import java.util.List;

public class AirportPerimeterSearchProvider implements PerimeterSearchProvider<Airport> {

    @Override
    public List<Airport> find(Geo geoPosition, int radius) throws Exception {
        Preconditions.checkArgument(radius <= 50000, "50000m Radius ist das Maximum.");

        LatLng latLngPosition = new LatLng(geoPosition.getLat(), geoPosition.getLon());

        GeoApiContext geoApiContext = GeoApiContextFactory.getBasicGeoApiContext();

        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, latLngPosition)
                .radius(radius)
                .type(PlaceType.AIRPORT);
        PlacesSearchResponse response = nearbySearchRequest.await();

        ArrayList<PlacesSearchResult> responseList = Lists.newArrayList(response.results);
        ArrayList<Airport> results = Lists.newArrayList();

        for (PlacesSearchResult r :
                responseList) {
            Airport airport = new Airport(r.geometry.location.lat, r.geometry.location.lng);
            airport.setName(r.name);
            results.add(airport);
        }
        return results;
    }

}
