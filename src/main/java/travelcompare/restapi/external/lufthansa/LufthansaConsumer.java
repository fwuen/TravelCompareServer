package travelcompare.restapi.external.lufthansa;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.lufthansa.response.*;

import java.util.Calendar;
import java.util.Date;

public class LufthansaConsumer extends Consumer {

    public static final String clientId = "utj2w984ec8utsps5jky9fxp";
    public static final String clientSecret = "bX9kw4CpG6";
    private String tokenType = "Bearer";
    private Date publicLastAuthenticated;
    private long publicExpiresIn;
    private String publicAccessToken;
    
    private Date partnerLastAuthenticated;
    private long partnerExpiresIn;
    private String partnerAccessToken;


    public LufthansaConsumer() {
        authenticate(false);
        authenticate(true);
    }

    @Override
    protected String getBaseURL() {
        return "https://api.lufthansa.com/v1/";
    }

    public FlightStatusResponse consumeFlightStatus(String flightNumber, String date) throws UnirestException {
        if(!publicStillAuthenticated()) authenticate(false);
        return Unirest.get(getBaseURL() + "operations/flightstatus/" + flightNumber + "/" + date).
                header("Authorization", tokenType + " " + publicAccessToken).
                asObject(FlightStatusResponse.class).
                getBody();
    }

    public FlightScheduleResponse consumeFlightSchedule(String origin, String destination, String date) throws UnirestException {
        if(!publicStillAuthenticated()) authenticate(false);
        return Unirest.get(getBaseURL() + "operations/schedules/" + origin + "/" + destination + "/" + date).
                header("Authorization", tokenType + " " + publicAccessToken).
                asObject(FlightScheduleResponse.class).
                getBody();
    }

    public NearestAirportResponse consumeNearestAirport(String lat, String lon) throws UnirestException {
        if(!publicStillAuthenticated()) authenticate(false);
        return Unirest.get(getBaseURL() + "references/airports/nearest/" + lat + "," + lon).
                header("Authorization", tokenType + " " + publicAccessToken).
                asObject(NearestAirportResponse.class).
                getBody();
    }
    
    public AllFaresResponse consumeAllFares(String catalogues, String origin, String destination, String travelDate) throws UnirestException {
        if(!partnerStillAuthenticated()) authenticate(true);
        return Unirest.get(getBaseURL() + "offers/fares/allfares").
                header("Authorization", tokenType + " " + partnerAccessToken).
                queryString("catalogues", catalogues).
                queryString("origin", origin).
                queryString("destination", destination).
                queryString("travel-date", travelDate).
                asObject(AllFaresResponse.class).
                getBody();
    }
    
    public LowestFaresResponse consumeLowestFares(String catalogues, String origin, String destination, String travelDate) throws UnirestException {
        if(!partnerStillAuthenticated()) authenticate(true);
        return Unirest.get(getBaseURL() + "offers/fares/lowestfares").
                header("Authorization", tokenType + " " + partnerAccessToken).
                queryString("catalogues", catalogues).
                queryString("origin", origin).
                queryString("destination", destination).
                queryString("travel-date", travelDate).
                asObject(LowestFaresResponse.class).
                getBody();
    }

    private void authenticate(boolean isPartnerRequest) {
        if(isPartnerRequest) {
            try {
                AuthenticationResponse response = Unirest.post(getBaseURL() + "partners/oauth/token").
                        field("client_id", clientId).
                        field("client_Secret", clientSecret).
                        field("grant_type", "client_credentials").
                        asObject(AuthenticationResponse.class).
                        getBody();
            partnerExpiresIn = response.getExpiresIn();
            partnerLastAuthenticated = response.getLastAuthenticated();
            partnerAccessToken = response.getAccessToken();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        } else {
            try {
                AuthenticationResponse response = Unirest.post(getBaseURL() + "oauth/token").
                        field("client_id", clientId).
                        field("client_Secret", clientSecret).
                        field("grant_type", "client_credentials").
                        asObject(AuthenticationResponse.class).
                        getBody();
            publicExpiresIn = response.getExpiresIn();
            publicLastAuthenticated = response.getLastAuthenticated();
            publicAccessToken = response.getAccessToken();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
    }
    private long getTimestampFromDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    private boolean publicStillAuthenticated() {
        if(getTimestampFromDate(publicLastAuthenticated) + publicExpiresIn < getTimestampFromDate(new Date())) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean partnerStillAuthenticated() {
        if(getTimestampFromDate(partnerLastAuthenticated) + partnerExpiresIn < getTimestampFromDate(new Date())) {
            return true;
        } else {
            return false;
        }
    }
}
