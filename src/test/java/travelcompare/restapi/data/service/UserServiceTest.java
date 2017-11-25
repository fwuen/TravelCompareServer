package travelcompare.restapi.data.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.provider.model.Geo;

@Transactional
public class UserServiceTest extends SpringTest {

    private static final String FIRST_NAME = "Dominic";
    private static final String LAST_NAME = "Fuchs";
    private static final String EMAIL = "syntarex@gmail.com";
    private static final String PASSWORD = "test123";
    private static final Geo GEO = new Geo(33.4, 21.2);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test(expected = NullPointerException.class)
    public void registerWithNull() {
        userService.register(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithInvalidData() {
        RegisterData registerData = RegisterData.builder().build();

        userService.register(registerData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerTwice() {
        userService.register(getValidRegisterData());

        userService.register(getValidRegisterData());
    }

    @Test
    public void register() {
        User user = userService.register(getValidRegisterData());

        Assert.assertNotNull(user);
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
        Assert.assertEquals(EMAIL, user.getEmail());
        Assert.assertTrue(passwordEncoder.matches(PASSWORD, user.getPassword()));
        Assert.assertEquals(GEO, user.getGeo());
    }

    private RegisterData getValidRegisterData() {
        return RegisterData.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .password2(PASSWORD)
                .location(GEO)
                .build();
    }

}
