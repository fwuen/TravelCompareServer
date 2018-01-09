package travelcompare.restapi.data.service;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travelcompare.restapi.api.model.request.ChangePasswordData;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.repository.UserRepository;
import travelcompare.restapi.provider.model.Geo;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public Optional<User> getUserByEmail(@NonNull String email) {
        Preconditions.checkArgument(
                EmailValidator.getInstance().isValid(email),
                "Die E-Mail Adresse ist ungültig."
        );

        return repository.findFirstByEmailEqualsIgnoreCase(email);
    }

    public Optional<User> getUserByID(long id) {
        Preconditions.checkArgument(id > 0);

        return repository.findFirstByIdEquals(id);
    }

    public User updateUser(@NonNull User user) {
        Preconditions.checkArgument(repository.existsByIdEquals(user.getId()));

        User oldUser = getUserByID(user.getId()).get();
        user.setPassword(oldUser.getPassword());

        return repository.save(user);
    }

    /* TODO: Funktioniert nicht */
    public void deleteUserByEmail(@NonNull String email) {
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(email));
        Preconditions.checkArgument(userExistsByEmail(email));

        repository.removeAllByEmailEqualsIgnoreCase(email);
    }

    public boolean userExistsByID(long id) {
        return repository.existsByIdEquals(id);
    }

    public boolean userExistsByEmail(@NonNull String email) {
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(email));

        return repository.existsByEmailEqualsIgnoreCase(email);
    }

    public void changePassword(
            @NonNull User user,
            @NonNull ChangePasswordData changePasswordData
    ) {
        Preconditions.checkArgument(userExistsByID(user.getId()));
        Preconditions.checkArgument(changePasswordData.valid().isValid(), changePasswordData.valid().getMessage());
        Preconditions.checkArgument(passwordEncoder.matches(changePasswordData.getOldPassword(), user.getPassword()), "Das alte Passwort war nicht korrekt.");

        user.setPassword(passwordEncoder.encode(changePasswordData.getPassword()));

        repository.save(user);
    }

    public User register(@NonNull RegisterData registerData) {
        Validation validation = registerData.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());
        Preconditions.checkArgument(!repository.findFirstByEmailEqualsIgnoreCase(registerData.getEmail()).isPresent());

        User user = new User();
        user.setFirstName(registerData.getFirstName());
        user.setLastName(registerData.getLastName());
        user.setEmail(registerData.getEmail());
        user.setGeo(new Geo(registerData.getLat(), registerData.getLon()));
        user.setPassword(passwordEncoder.encode(registerData.getPassword()));

        return repository.save(user);
    }

}
