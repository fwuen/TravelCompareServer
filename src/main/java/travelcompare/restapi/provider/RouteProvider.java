package travelcompare.restapi.provider;

import com.google.maps.errors.ApiException;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.io.IOException;

public interface RouteProvider {
    public Route getRoute(Geo start, Geo destination) throws Exception;
}
