package travelcompare.restapi.provider.route;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;
import java.util.List;

public interface RoutesProvider<T extends Geo> {

    public List<Route> find(T start, T destination, Date date) throws Exception;

}
