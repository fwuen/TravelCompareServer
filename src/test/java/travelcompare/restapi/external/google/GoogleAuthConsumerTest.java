package travelcompare.restapi.external.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.minidev.json.annotate.JsonIgnore;
import org.junit.Test;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConsumer;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConstants;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleAuthConsumerTest {

    @JsonIgnore
    @Test
    public void TestGoogleVerify() throws IOException, GeneralSecurityException, UnirestException {
        GoogleAuthResponse response = new GoogleAuthConsumer().getUserInfo(GoogleAuthConstants.TEST_ACCESS_TOKEN);
        System.out.print(response.getName());
    }

    @JsonIgnore
    @Test
    public void TestRedeemAuthToken() throws IOException {
        GoogleTokenResponse reponse = new GoogleAuthConsumer().redeemAuthToken(GoogleAuthConstants.AUTH_TOKEN);
        System.out.print(reponse.getRefreshToken());
    }

    @JsonIgnore
    @Test
    public void TestGetNewAccessToken() throws IOException, GeneralSecurityException, UnirestException {
        String accessToken = new GoogleAuthConsumer().refreshAccessToken(GoogleAuthConstants.TEST_REFRESH_TOKEN);
        System.out.print(accessToken);
    }
}
