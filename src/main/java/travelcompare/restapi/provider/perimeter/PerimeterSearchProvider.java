package travelcompare.restapi.provider.perimeter;

import travelcompare.restapi.provider.model.Geo;

import java.util.List;

public interface PerimeterSearchProvider<T extends Geo> {

    public List<T> findNearest(Geo position) throws Exception;

}
