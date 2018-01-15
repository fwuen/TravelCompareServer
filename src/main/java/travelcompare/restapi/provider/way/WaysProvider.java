package travelcompare.restapi.provider.way;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;

import java.util.Date;
import java.util.List;

public interface WaysProvider<T extends Geo> {

    public List<Route> find(T start, T destination, Date date) throws Exception;

}
