package travelcompare.restapi.data.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.api.model.User;

import java.util.Optional;

@Transactional
public class UserRepositoryTest extends RepositoryTest {

    private static final String firstName = "Dominic";
    private static final String lastName = "Fuchs";
    private static final String email = "syntarex@gmail.com";



    @Before
    public void insertUser() {
        User user = new User(
                firstName,
                lastName,
                email
        );

        userRepository.save(user);
    }

    @Test
    public void findFirstByEmailEqualsIgnoreCase() {
        Optional<User> optional = userRepository.findFirstByEmailEqualsIgnoreCase(email.toUpperCase());

        Assert.assertTrue(optional.isPresent());

        User user = optional.get();

        Assert.assertNotNull(user);
        Assert.assertEquals(firstName, user.getFirstName());
        Assert.assertEquals(lastName, user.getLastName());
        Assert.assertEquals(email, user.getEmail());
    }

}
