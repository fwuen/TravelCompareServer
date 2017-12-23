package travelcompare.restapi.api.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class GoogleOAuthData implements Validateable {

    // Auth-Code von Google über welchen der Nutzer sich verifizieren kann
    private String auth_code;

    @Override
    public Validation valid() {
        Validation validation = new Validation(true, "");

        if (auth_code == null || auth_code.isEmpty()) {
            validation.setError("Der Google-Authentifizierungscode ist ungültig!");
            return validation;
        }
        return validation;
    }
}
