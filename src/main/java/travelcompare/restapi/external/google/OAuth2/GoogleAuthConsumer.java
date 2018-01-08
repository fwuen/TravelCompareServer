package travelcompare.restapi.external.google.OAuth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import travelcompare.restapi.external.Consumer;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;
import travelcompare.restapi.external.google.OAuth2.util.GoogleAuthHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Endpunkt für Google Oauth2
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
     */
    public GoogleAuthResponse getUserInfo(String accessToken) throws IOException, GeneralSecurityException, UnirestException {
        HttpResponse httpResponse = Unirest.get(getBaseURL() + GoogleAuthConstants.USERINFO_URL).
                header("Authorization", GoogleAuthConstants.TOKEN_TYPE + " " + accessToken).
                header("Content-length", "0").
                header("Host", "www.googleapis.com").
                asJson();

        String jsonResponseString = httpResponse.getBody().toString();
        JSONObject jsonObject = new JSONObject(jsonResponseString);

        return new GoogleAuthHelper().createGoogleAuthResponse(jsonObject);
    }

    /**
     * Einlösen des Auth Codes von Google gegen eine GoogleTokenReponse bestehend aus access_token, expires_in, id_token, refresh_token, token_type
     *
     * @param authCode String
     * @return GoogleTokenResponse;
     * @throws IOException exception
     */
    public GoogleTokenResponse redeemAuthToken(String authCode, String clientId, String redirectUri) throws IOException {

        return new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                "https://www.googleapis.com/oauth2/v4/token",
                clientId, // Die Id des Clients
                GoogleAuthConstants.SECRET,
                authCode,
                redirectUri)  // Specify the same redirect URI that you use with your web app
                .execute();
    }

    /**
     * Refreshen des AccessTokens durch den gespeicherten RefreshToken
     *
     * @param refreshToken String
     * @return String
     */
    public String refreshAccessToken(String refreshToken) throws UnirestException, IOException, GeneralSecurityException {

        GoogleCredential credential = createCredentialWithRefreshToken(new NetHttpTransport(), new JacksonFactory(), new TokenResponse().setRefreshToken(refreshToken));
        credential.refreshToken();

        return credential.getAccessToken();
    }


    /**
     * Erstellen der GoogleCredentials anhand des gespeicherten RefreshTokens
     *
     * @param transport     NetHttptTransport
     * @param jsonFactory   JacksonFactory
     * @param tokenResponse TokenResponse
     * @return GoogleCredential
     */
    private GoogleCredential createCredentialWithRefreshToken(NetHttpTransport transport,
                                                              JacksonFactory jsonFactory, TokenResponse tokenResponse) {
        return new GoogleCredential.Builder().setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(GoogleAuthConstants.CLIENT_ID, GoogleAuthConstants.SECRET)
                .build()
                .setFromTokenResponse(tokenResponse);
    }
}
