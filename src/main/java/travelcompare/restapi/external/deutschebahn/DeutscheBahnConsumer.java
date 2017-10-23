package travelcompare.restapi.external.deutschebahn;

import travelcompare.restapi.external.Consumer;

public class DeutscheBahnConsumer extends Consumer {

    @Override
    protected String getBaseURL() {
        return "https://developer.deutschebahn.com/freeplan/v1/";
    }

}
