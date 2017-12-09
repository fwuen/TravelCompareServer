package travelcompare.restapi.provider.way;

import travelcompare.restapi.provider.model.Airport;
import travelcompare.restapi.provider.model.Way;

import java.util.Date;
import java.util.List;

public class AirportWaysProvider implements WaysProvider<Airport> {

    @Override
    public List<Way> find(Airport start, Airport destination, Date date) {
        return null;
    }

}
