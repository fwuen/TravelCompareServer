package travelcompare.restapi.api.model.request;

import lombok.*;
import org.apache.commons.validator.routines.EmailValidator;
import travelcompare.restapi.api.configuration.ValidationConfiguration;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ChangePasswordData implements Validateable {

    @NonNull private String oldPassword;
    @NonNull private String password;
    @NonNull private String password2;

    @Override
    public Validation valid() {
        Validation validation = new Validation(true, "");

        if(oldPassword.length() < ValidationConfiguration.PASSWORD_MIN_LENGTH) {
            validation.setError("Das alte Passwort ist zu kurz.");
            return validation;
        }

        if(password.length() < ValidationConfiguration.PASSWORD_MIN_LENGTH) {
            validation.setError("Das neue Passwort ist zu kurz.");
        }

        if(!password.equals(password2)) {
            validation.setError("Die Passwörter stimmen nicht überein.");
        }

        return validation;
    }

}
