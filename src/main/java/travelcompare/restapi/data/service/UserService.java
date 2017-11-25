package travelcompare.restapi.data.service;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import travelcompare.restapi.data.repository.UserRepository;
import travelcompare.restapi.api.model.User;

import java.util.Optional;

/* TODO: Test */
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> getUserByEmail(@NonNull String email) {
        Preconditions.checkArgument(
                EmailValidator.getInstance().isValid(email),
                "Die E-Mail Adresse ist ungültig."
        );

        return repository.findFirstByEmailEqualsIgnoreCase(email);
    }

}
