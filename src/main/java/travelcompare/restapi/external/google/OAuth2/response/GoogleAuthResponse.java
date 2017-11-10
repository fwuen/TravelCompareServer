package travelcompare.restapi.external.google.OAuth2.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Antwort auf den "/user/info"-Request an Google
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GoogleAuthResponse {

    /**
     * Id des GoogleUsers
     */
    private String userId;

    /**
     * Email Adresse des GoogleUsers
     */
    private String email;

    /**
     * Status der Email-Verifizierung
     */
    private boolean emailVerified;

    /**
     * Name des GoogleUsers
     */
    private String name;

    /**
     * Url des Profilbilds des GoogleUsers
     */
    private String pictureUrl;

    /**
     * Herkunftsland des Users
     */
    private String locale;

    /**
     * Vollständiger Name des GoogleUsers
     */
    private String familyName;

    /**
     * Angegebener Name des GoogleUsers
     */
    private String givenName;

    /**
     * Link zum Googleprofil des Users
     */
    private String link;
}