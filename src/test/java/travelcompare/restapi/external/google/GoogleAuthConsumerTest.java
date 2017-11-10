package travelcompare.restapi.external.google;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConsumer;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConstants;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleAuthConsumerTest {

    @Test
    public void TestGoogleVerify() throws IOException, GeneralSecurityException, UnirestException {
        String accessToken = GoogleAuthConstants.TEST_ACCESS_TOKEN;
        GoogleAuthResponse response = new GoogleAuthConsumer().verifyToken(accessToken);

        if (response.getName() != null) {
            System.out.println(response.getName());
        } else {
            System.out.println("Penis");
        }
    }
}
