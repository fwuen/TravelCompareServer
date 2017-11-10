package travelcompare.restapi.external.google;

import com.google.maps.GeoApiContext;

public class GeoApiContextFactory {

    private static final String API_KEY = "AIzaSyCWwqKwqO5jt9B7GjM5h0zw9aPzo1eWMLI";

    public static GeoApiContext getBasicGeoApiContext() {
        return new GeoApiContext.Builder().apiKey(API_KEY).build();
    }

}
