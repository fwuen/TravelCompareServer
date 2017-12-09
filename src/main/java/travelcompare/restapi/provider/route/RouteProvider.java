package travelcompare.restapi.provider.route;

import com.google.maps.errors.ApiException;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;

public interface RouteProvider<T extends Geo> {
    public Route getRoute(T start, T destination) throws Exception;
    
    public Route getRouteOnDate(T start, T destination, Date travelDate) throws Exception;
}
