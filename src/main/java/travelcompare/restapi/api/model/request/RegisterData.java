package travelcompare.restapi.api.model.request;

import lombok.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import travelcompare.restapi.provider.model.Geo;


@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class RegisterData implements Validateable {

    private static final int PASSWORD_MIN_LENGTH = 3;



    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private String password;
    @NonNull private String password2;
    private Geo location;



    @Override
    public Validation valid() {
        Validation validation = new Validation(true, "");

        if(firstName.length() <= 0) {
            validation.setError("Der Vorname darf nicht leer sein.");
            return validation;
        }

        if(lastName.length() <= 0) {
            validation.setError("Der Nachname darf nicht leer sein.");
            return validation;
        }

        if(!EmailValidator.getInstance().isValid(email)) {
            validation.setError("Die E-Mail Adresse muss gültig sein.");
            return validation;
        }

        if(password.length() < PASSWORD_MIN_LENGTH) {
            validation.setError("Das Passwort muss mindestens " + PASSWORD_MIN_LENGTH + " Zeichen haben");
            return validation;
        }

        if(!password.equals(password2)) {
            validation.setError("Die Passwörter stimmen nicht überein.");
            return validation;
        }

        return validation;
    }

}
