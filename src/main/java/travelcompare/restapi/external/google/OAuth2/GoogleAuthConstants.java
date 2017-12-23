package travelcompare.restapi.external.google.OAuth2;

public class GoogleAuthConstants {

    /**
     * BaseURL von Gooogle
     */
    static final String BASE_URL = "https://www.googleapis.com";

    /**
     * Endpunkt /auth/userinfo für das Erhalten der Daten hinter dem AccessToken
     */
    static final String USERINFO_URL = "/oauth2/v2/userinfo";

    /**
     * ClientID für OAuth2 unseres Clients
     */
    static final String CLIENT_ID = "253237769892-72o9h4cvaia73n3i728vdee3k0iatfhi.apps.googleusercontent.com";

    /**
     * SecretID für OAuth2
     */
    static final String SECRET = "ipS_kzBAYR-HALSWCkXfE92c";

    /**
     * TokenType des GoogleAccesstTokens
     */
    static final String TOKEN_TYPE = "Bearer";

    /**
     * TestToken von "https://developers.google.com/oauthplayground"
     */
    public static String TEST_ACCESS_TOKEN = "ya29.GlsKBR8oCwO0S21kQ21QPA7Fby2V8xy95rT_B0FZEzOnSzrmb8RHfiMOyeltylrD_WZMieZ6Weromgi4Hc4dJFz1wE5qb1tNsyzcTvd40bQoaDnEHTFB47o-HYmN";

    /**
     * TestRefreshToken
     */
    public static String TEST_REFRESH_TOKEN = "1/EqCF5qBfyQPzm1Ea3gQa8aPAziVtgQLBHzWCrpCYdSE";

    /**
     * AUTH_TOKEN
     */
    public static String AUTH_TOKEN = "4/RUBWiV4RMCf_Gvysyp1USPHWdb8-RSeytecnoWUB1h8";
}


