package travelcompare.restapi.external.lufthansa;

import travelcompare.restapi.external.Consumer;

public class LufthansaConsumer extends Consumer {
    
    @Override
    protected String getBaseURL() { return "https://api.lufthansa.com/v1/references/"; }
}
