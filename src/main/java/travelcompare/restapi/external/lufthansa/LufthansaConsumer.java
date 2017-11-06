package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.lufthansa.response.AuthenticationResponse;
import travelcompare.restapi.external.lufthansa.response.FlightStatusResponse;

public class LufthansaConsumer extends Consumer {

    public static final String clientId = "q8mtx3rptwnjr3a2k9fvne6p";
    public static final String clientSecret = "cz4UKEkjtP";
    public static String accessToken;

    @Override
    protected String getBaseURL() {
        return "https://api.lufthansa.com/v1/";
    }

    public FlightStatusResponse consumeFlightStatus(String flightNumber, String date) throws UnirestException {
        return Unirest.get(getBaseURL() + "operations/flightstatus/" + flightNumber + "/" + date).
                header("Authorization", "Bearer " + accessToken).
                asObject(FlightStatusResponse.class).
                getBody();
    }

    public AuthenticationResponse authenticate() throws UnirestException {
        AuthenticationResponse response =
                Unirest.post(getBaseURL() + "oauth/token").
                field("client_id", clientId).
                field("client_Secret", clientSecret).
                field("grant_type", "client_credentials").
                asObject(AuthenticationResponse.class).
                getBody();

        accessToken = response.getAccessToken();

        return response;
    }
}
