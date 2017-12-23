package travelcompare.restapi.data.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travelcompare.restapi.api.model.request.GoogleOAuthData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.repository.UserRepository;
import travelcompare.restapi.external.google.OAuth2.GoogleAuthConsumer;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

import java.util.Optional;
import java.util.Random;

@Service
public class GoogleOAuthService {
    @Autowired
    private UserRepository repository;

    /**
     * Google-Auth-Code gegen die dahinterliegenden Token eintauschen
     *
     * @param data GoogleOAuthData
     * @return Optional<GoogleTokenResponse>
     */
    public Optional<GoogleTokenResponse> getGoogleToken(@NonNull GoogleOAuthData data) {
        Validation validation = data.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());
        GoogleAuthConsumer consumer = new GoogleAuthConsumer();
        try {
            GoogleTokenResponse response = consumer.redeemAuthToken(data.getAuth_code());
            return Optional.ofNullable(response);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Über den AccessToken die Daten des Users holen
     *
     * @param accessToken GoogleOAuthData
     * @return Optional<GoogleAuthResponse>
     */
    public Optional<GoogleAuthResponse> getGoogleUserDetails(String accessToken) {
        try {
            GoogleAuthResponse googleAuthResponse = new GoogleAuthConsumer().getUserInfo(accessToken);
            return Optional.ofNullable(googleAuthResponse);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Einen Google Nutzer anlegen
     * Das Passwort wird dabei generiert wobei der User sich nie über dieses anmeldet
     *
     * @param googleAuthResponse GoogleAuthResponse
     * @return User
     */
    public User createGoogleUser(GoogleAuthResponse googleAuthResponse) {

        User user = new User();
        user.setEmail(googleAuthResponse.getEmail());
        user.setFirstName(googleAuthResponse.getGivenName());
        user.setLastName(googleAuthResponse.getFamilyName());
        user.setGoogleId(googleAuthResponse.getUserId());
        user.setPassword(generate());
        return repository.save(user);
    }

    /**
     * Generieren eines zufälligen Passwort für Googleuser
     *
     * @return String
     */
    private String generate() {
        StringBuilder stringBuilder = new StringBuilder();
        final char[] ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-+!$%&/()={}?".toCharArray();
        int tokenLength = 64;
        Random random = new Random();
        for (int i = 0; i < tokenLength; i++)
            stringBuilder.append(ALLOWED_CHARS[random.nextInt(ALLOWED_CHARS.length)]);

        String token = stringBuilder.toString();
        Verify.verify(token.length() == tokenLength);

        return token;
    }
}
