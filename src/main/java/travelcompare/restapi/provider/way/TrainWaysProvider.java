package travelcompare.restapi.provider.way;

import travelcompare.restapi.provider.model.TrainStation;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.List;

public class TrainWaysProvider implements WaysProvider<TrainStation> {

    @Override
    public List<Way> find(TrainStation start, TrainStation destination, Date date) {
        return null;
    }

}
