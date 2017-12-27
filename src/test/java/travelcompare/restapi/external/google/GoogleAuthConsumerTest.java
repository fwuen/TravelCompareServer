package travelcompare.restapi.external.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Ignore;
import org.junit.Test;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConstants;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConsumer;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleAuthConsumerTest {

    @Ignore
    @Test
    public void TestGoogleVerify() throws IOException, GeneralSecurityException, UnirestException {
        GoogleAuthResponse response = new GoogleAuthConsumer().getUserInfo(GoogleAuthConstants.TEST_ACCESS_TOKEN);
        System.out.print(response.getName());
    }

    @Ignore
    @Test
    public void TestRedeemAuthToken() throws IOException {
        GoogleTokenResponse response = new GoogleAuthConsumer().redeemAuthToken(GoogleAuthConstants.AUTH_TOKEN, GoogleAuthConstants.CLIENT_ID);
        System.out.print(response.getRefreshToken());
    }

    @Ignore
    @Test
    public void TestGetNewAccessToken() throws IOException, GeneralSecurityException, UnirestException {
        String accessToken = new GoogleAuthConsumer().refreshAccessToken(GoogleAuthConstants.TEST_REFRESH_TOKEN);
        System.out.print(accessToken);
    }
}
