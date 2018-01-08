package travelcompare.restapi.api.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GoogleOAuthData implements Validateable {

    // Auth-Code von Google über welchen der Nutzer sich verifizieren kann
    private String auth_code;
    private String client_id;
    private String redirect_uri;

    @Override
    public Validation valid() {
        Validation validation = new Validation(true, "");

        if (auth_code == null || auth_code.isEmpty()) {
            validation.setError("Der Google-Authentifizierungscode ist ungültig!");
            return validation;
        }
        if (client_id == null || client_id.isEmpty()) {
            validation.setError("Die Client-Id ist ungültig!");
            return validation;
        }
        if (redirect_uri == null || redirect_uri.isEmpty()) {
            validation.setError("Die RedirectUri ist ungültig!");
            return validation;
        }
        return validation;
    }
}
