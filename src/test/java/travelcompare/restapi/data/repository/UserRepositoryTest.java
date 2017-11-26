package travelcompare.restapi.data.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.data.model.User;

import java.util.Optional;

@Transactional
public class UserRepositoryTest extends RepositoryTest {

    private static final String FIRST_NAME = "Dominic";
    private static final String LAST_NAME = "Fuchs";
    private static final String EMAIL = "syntarex@gmail.com";
    private static final String PASSWORD = "test123";


    @Before
    public void insertUser() {
        User user = new User();

        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        userRepository.save(user);
    }

    @Test
    public void findFirstByEmailEqualsIgnoreCase() {
        Optional<User> optional = userRepository.findFirstByEmailEqualsIgnoreCase(EMAIL.toUpperCase());

        Assert.assertTrue(optional.isPresent());

        User user = optional.get();

        Assert.assertNotNull(user);
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
        Assert.assertEquals(EMAIL, user.getEmail());
    }

}
