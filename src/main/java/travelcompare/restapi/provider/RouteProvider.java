package travelcompare.restapi.provider;

import com.google.maps.errors.ApiException;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

public interface RouteProvider<T extends Geo> {
    public Route getRoute(T start, T destination) throws Exception;
}
