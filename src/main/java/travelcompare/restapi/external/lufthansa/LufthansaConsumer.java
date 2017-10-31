package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.lufthansa.response.FlightStatusResponse;

public class LufthansaConsumer extends Consumer {
    
    @Override
    protected String getBaseURL() { return "https://api.lufthansa.com/v1/operations/flightstatus/"; }

    public FlightStatusResponse consumeFlightStatus(String flightNumber, String date) throws UnirestException {
        return Unirest.get(getBaseURL() + flightNumber + "/" + date).
                header("Authorization", "Bearer 28k9mmrsghjstj599bcbye3c").
                asObject(FlightStatusResponse.class).
                getBody();
    }
}
