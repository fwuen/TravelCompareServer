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
import travelcompare.restapi.external.lufthansa.LufthansaConsumer;
import travelcompare.restapi.external.lufthansa.response.NearestAirportResponse;
import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Geo;

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
    
    public List<Airport> findByLh(Geo geoPosition) throws Exception {
        LufthansaConsumer consumer = new LufthansaConsumer();
        NearestAirportResponse response = consumer.consumeNearestAirport("" + geoPosition.getLat(), "" + geoPosition.getLon());
        List<Airport> airports = Lists.newArrayList();
        
        for(travelcompare.restapi.external.lufthansa.model.Airport airport : response.getNearestAirportResource().getAirports().getAirport()) {
            Airport newAirport = new Airport(airport.getPosition().getCoordinate().getLatitude(), airport.getPosition().getCoordinate().getLongitude());
            newAirport.setIdentifier(airport.getAirportCode());
            newAirport.setName(airport.getNames().getNames().get(0).getName());
        }
        
        return airports;
    }

}
