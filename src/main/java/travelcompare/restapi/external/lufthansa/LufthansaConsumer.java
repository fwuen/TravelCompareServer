package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.lufthansa.response.AuthenticationResponse;
import travelcompare.restapi.external.lufthansa.response.FlightScheduleResponse;
import travelcompare.restapi.external.lufthansa.response.FlightStatusResponse;
import travelcompare.restapi.external.lufthansa.response.NearestAirportResponse;

import java.util.Calendar;
import java.util.Date;

public class LufthansaConsumer extends Consumer {

    public static final String clientId = "utj2w984ec8utsps5jky9fxp";
    public static final String clientSecret = "bX9kw4CpG6";
    private String accessToken;
    private String tokenType = "Bearer";
    private Date lastAuthenticated;
    private long expiresIn;


    public LufthansaConsumer() {
        authenticate();
    }

    @Override
    protected String getBaseURL() {
        return "https://api.lufthansa.com/v1/";
    }

    public FlightStatusResponse consumeFlightStatus(String flightNumber, String date) throws UnirestException {
        if(!stillAuthenticated()) authenticate();
        return Unirest.get(getBaseURL() + "operations/flightstatus/" + flightNumber + "/" + date).
                header("Authorization", tokenType + " " + accessToken).
                asObject(FlightStatusResponse.class).
                getBody();
    }

    public FlightScheduleResponse consumeFlightSchedule(String origin, String destination, String date) throws UnirestException {
        if(!stillAuthenticated()) authenticate();
        return Unirest.get(getBaseURL() + "operations/schedules/" + origin + "/" + destination + "/" + date).
                header("Authorization", tokenType + " " + accessToken).
                asObject(FlightScheduleResponse.class).
                getBody();
    }

    public NearestAirportResponse consumeNearestAirport(String lat, String lon) throws UnirestException {
        if(!stillAuthenticated()) authenticate();
        return Unirest.get(getBaseURL() + "references/airports/nearest/" + lat + "," + lon).
                header("Authorization", tokenType + " " + accessToken).
                asObject(NearestAirportResponse.class).
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

        lastAuthenticated = response.getLastAuthenticated();
        accessToken = response.getAccessToken();
        expiresIn = response.getExpiresIn();
    }

    private long getTimestampFromDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    private boolean stillAuthenticated() {
        if(getTimestampFromDate(lastAuthenticated) + expiresIn < getTimestampFromDate(new Date())) {
            return true;
        } else {
            return false;
        }
    }
}
