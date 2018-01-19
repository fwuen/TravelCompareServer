package travelcompare.restapi.external.bahnprice;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;

public class BahnPriceConsumer extends Consumer {

    @Override
    protected String getBaseURL() {
        return "http://localhost:8086/";
    }


    public double getBahnPrice(String distance, String type) throws UnirestException {
        String price = Unirest.get(getBaseURL() + "price").
                queryString("distance", distance).
                queryString("type", type).asString().getBody();
        return 50.0;
    }
}
