package travelcompare.restapi.api.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.configuration.SecurityConfiguration;
import travelcompare.restapi.api.model.request.GoogleOAuthData;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.service.GoogleOAuthService;
import travelcompare.restapi.data.service.UserService;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

import java.util.Date;
import java.util.Optional;

@RestController
public class GoogleAuthController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Autowired
    private UserService userService;

    /**
     * Den GoogleAuthCode verifizieren und so entweder einen neuen User anlegen und diesen manuell authentifizieren
     * oder einen bestehenden User authentifizieren
     *
     * @param data GoogleAuthData
     * @return ResponseEntity<String>
     */
    @PostMapping(RestURLs.OAUTH)
    public ResponseEntity<String> googleSignIn(
            @RequestBody GoogleOAuthData data) {
        if (!data.valid().isValid())
            return ResponseEntity.status(400).build();

        Optional<GoogleTokenResponse> googleTokenResponse = googleOAuthService.getGoogleToken(data);
        if (!googleTokenResponse.isPresent() || googleTokenResponse.get().getAccessToken().isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        Optional<GoogleAuthResponse> googleAuthResponse = googleOAuthService.getGoogleUserDetails(googleTokenResponse.get().getAccessToken());
        if (!googleAuthResponse.isPresent() ||
                googleAuthResponse.get().getEmail().isEmpty() ||
                googleAuthResponse.get().getUserId().isEmpty() ||
                googleAuthResponse.get().getFamilyName().isEmpty() ||
                googleAuthResponse.get().getGivenName().isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        Optional<User> userOptional = userService.getUserByEmail(googleAuthResponse.get().getEmail());
        User user = null;
        if (!userOptional.isPresent()) {
            user = googleOAuthService.createGoogleUser(googleAuthResponse.get());
        }

        if (user == null) {
            return ResponseEntity.status(500).build();
        }
        // User manuell authentifizieren und den Token im Responseheader zurückgeben
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Lists.newArrayList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConfiguration.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConfiguration.SECRET.getBytes())
                .compact();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("Authorization", SecurityConfiguration.TOKEN_PREFIX + token);
        return new ResponseEntity<>("", responseHeader, HttpStatus.OK);
    }
}
