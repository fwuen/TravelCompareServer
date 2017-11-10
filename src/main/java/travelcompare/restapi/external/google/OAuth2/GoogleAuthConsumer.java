package travelcompare.restapi.external.google.OAuth2;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;
import travelcompare.restapi.external.google.OAuth2.util.GoogleAuthHelper;

import java.io.*;
import java.security.GeneralSecurityException;

/**
 * Endpunkt zur Verifizierung des AccessTokens, welcher vom Frontend kommmt
 */
public class GoogleAuthConsumer extends Consumer {


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getBaseURL() {
        return GoogleAuthConstants.BASE_URL;
    }

    /**
     * Verifiziert den AccessToken von Google und gibt die Daten, die hinter dem Token sind zurück
     *
     * @param accessToken String
     * @return GoogleAuthResponse response
     * @throws IOException              exception
     * @throws GeneralSecurityException exception
     * @throws UnirestException         exception
     * @todo Fehlerklassen anlegen für die möglicherweise auftretenden Fehler
     */
    public GoogleAuthResponse verifyToken(String accessToken) throws IOException, GeneralSecurityException, UnirestException {
        HttpResponse httpResponse = Unirest.get(getBaseURL()).
                header("Authorization", GoogleAuthConstants.TOKEN_TYPE + " " + accessToken).
                header("Content-length", "0").
                header("Host", "www.googleapis.com").
                asJson();

        String jsonResponseString = httpResponse.getBody().toString();
        JSONObject jsonObject = new JSONObject(jsonResponseString);
        GoogleAuthResponse googleAuthResponse = new GoogleAuthHelper().createGoogleAuthResponse(jsonObject);

        return googleAuthResponse;
    }
}
