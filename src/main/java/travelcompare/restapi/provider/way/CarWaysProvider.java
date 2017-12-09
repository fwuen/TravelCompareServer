package travelcompare.restapi.provider.way;

import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.List;

public class CarWaysProvider implements WaysProvider<Geo> {

    @Override
    public List<Way> find(Geo start, Geo destination, Date date) {
        return null;
    }

}
