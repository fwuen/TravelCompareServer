package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.lufthansa.response.AuthenticationResponse;
import travelcompare.restapi.external.lufthansa.response.FlightStatusResponse;

public class LufthansaConsumer extends Consumer {

    public static final String clientId = "q8mtx3rptwnjr3a2k9fvne6p";
    public static final String clientSecret = "cz4UKEkjtP";
    private String accessToken;
    private String tokenType = "Bearer";
    private long expiresIn;


    public LufthansaConsumer() {
        authenticate();
    }

    @Override
    protected String getBaseURL() {
        return "https://api.lufthansa.com/v1/";
    }

    public FlightStatusResponse consumeFlightStatus(String flightNumber, String date) throws UnirestException {
        return Unirest.get(getBaseURL() + "operations/flightstatus/" + flightNumber + "/" + date).
                header("Authorization", tokenType + " " + accessToken).
                asObject(FlightStatusResponse.class).
                getBody();
    }

    private void authenticate() {
        AuthenticationResponse response =
                null;
        try {
            response = Unirest.post(getBaseURL() + "oauth/token").
            field("client_id", clientId).
            field("client_Secret", clientSecret).
            field("grant_type", "client_credentials").
            asObject(AuthenticationResponse.class).
            getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        accessToken = response.getAccessToken();
        expiresIn = response.getExpiresIn();
    }
}
