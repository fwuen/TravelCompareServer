package travelcompare.restapi.provider.way;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.List;

public interface WaysProvider<T extends Geo> {

    public List<Way> find(T start, T destination, Date date) throws Exception;

}
