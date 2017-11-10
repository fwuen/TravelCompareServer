package travelcompare.restapi.external.google.OAuth2.util;

import org.json.JSONException;
import org.json.JSONObject;
import travelcompare.restapi.external.google.OAuth2.response.GoogleAuthResponse;

public class GoogleAuthHelper {

    /**
     * Helper zum Erstellen einer GoogleAuthResponse
     *
     * @param json JSONObject
     * @return GoogleAuthResponse
     */
    public GoogleAuthResponse createGoogleAuthResponse(JSONObject json) {
        GoogleAuthResponse googleAuthResponse = new GoogleAuthResponse();
        try {
            googleAuthResponse.setName(json.getString("name"));
        } catch (JSONException e) {
            googleAuthResponse.setName("");
        }
        try {
            googleAuthResponse.setEmail(json.getString("email"));
        } catch (JSONException e) {
            googleAuthResponse.setEmail("");
        }
        try {
            googleAuthResponse.setEmailVerified(json.getBoolean("verified_email"));
        } catch (JSONException e) {
            googleAuthResponse.setEmailVerified(false);
        }
        try {
            googleAuthResponse.setFamilyName(json.getString("family_name"));
        } catch (JSONException e) {
            googleAuthResponse.setFamilyName("");
        }
        try {
            googleAuthResponse.setGivenName(json.getString("given_name"));
        } catch (JSONException e) {
            googleAuthResponse.setGivenName("");
        }
        try {
            googleAuthResponse.setLink(json.getString("link"));
        } catch (JSONException e) {
            googleAuthResponse.setLink("");
        }
        try {
            googleAuthResponse.setLocale(json.getString("locale"));
        } catch (JSONException e) {
            googleAuthResponse.setLocale("");
        }
        try {
            googleAuthResponse.setPictureUrl(json.getString("picture"));
        } catch (JSONException e) {
            googleAuthResponse.setPictureUrl("");
        }
        try {
            googleAuthResponse.setUserId(json.getString("id"));
        } catch (JSONException e) {
            googleAuthResponse.setUserId("");
        }
        return googleAuthResponse;
    }
}
