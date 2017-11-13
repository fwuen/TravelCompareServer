package travelcompare.restapi.data.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.model.User;

import java.util.Optional;

@Transactional
public class UserRepositoryTest extends SpringTest {

    private static final String firstName = "Dominic";
    private static final String lastName = "Fuchs";
    private static final String email = "syntarex@gmail.com";



    @Autowired
    private UserRepository userRepository;



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
