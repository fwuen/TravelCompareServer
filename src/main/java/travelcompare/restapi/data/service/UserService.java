package travelcompare.restapi.data.service;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /* TODO: Test */
    public Optional<User> getUserByEmail(@NonNull String email) {
        Preconditions.checkArgument(
                EmailValidator.getInstance().isValid(email),
                "Die E-Mail Adresse ist ungültig."
        );

        return repository.findFirstByEmailEqualsIgnoreCase(email);
    }

    public User register(@NonNull RegisterData registerData) {
        Validation validation = registerData.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());
        Preconditions.checkArgument(!repository.findFirstByEmailEqualsIgnoreCase(registerData.getEmail()).isPresent());

        User user = new User();
        user.setFirstName(registerData.getFirstName());
        user.setLastName(registerData.getLastName());
        user.setEmail(registerData.getEmail());
        user.setGeo(registerData.getLocation());
        user.setPassword(passwordEncoder.encode(registerData.getPassword()));

        return repository.save(user);
    }

}
