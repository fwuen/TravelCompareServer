package travelcompare.restapi.external.tankerkoenig;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.tankerkoenig.response.RangeSearchResponse;

public class TankerkoenigConsumer extends Consumer {

    private static final String apiKey = "57bdc10a-38e9-21e8-f0c9-e7fd65234e42";

    @Override
    protected String getBaseURL() {
        return "https://creativecommons.tankerkoenig.de/json/";
    }

    public RangeSearchResponse consume() throws UnirestException {
            return Unirest.get(getBaseURL() + "list.php").
                    queryString("apikey", apiKey).
                    queryString("lat", "50.212932").
                    queryString("lng", "11.943976").
                    queryString("rad", "25").
                    queryString("type", "all").
                    queryString("sort", "dist").
                    asObject(RangeSearchResponse.class).getBody();
    }
}
