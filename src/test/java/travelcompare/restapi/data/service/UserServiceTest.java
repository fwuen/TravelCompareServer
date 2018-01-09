package travelcompare.restapi.data.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import travelcompare.restapi.SpringTest;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.data.model.User;

@Transactional
public class UserServiceTest extends SpringTest {

    private static final String FIRST_NAME = "Dominic";
    private static final String LAST_NAME = "Fuchs";
    private static final String EMAIL = "syntarex@gmail.com";
    private static final String PASSWORD = "test123";
    private static final double LAT = 33.4;
    private static final double LON = 21.2;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test(expected = NullPointerException.class)
    public void registerWithNull() {
        userService.register(null);
    }

    @Test(expected = NullPointerException.class)
    public void registerWithEmptyData() {
        userService.register(RegisterData.builder().build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerWithInvalidData() {
        userService.register(getInvalidRegisterData());
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
        Assert.assertEquals(LAT, user.getGeo().getLat(), 0);
        Assert.assertEquals(LON, user.getGeo().getLon(), 0);
    }

    private RegisterData getInvalidRegisterData() {
        return RegisterData.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email("keine email")
                .password(PASSWORD)
                .password2(PASSWORD)
                .lat(LAT)
                .lon(LON)
                .build();
    }

    private RegisterData getValidRegisterData() {
        return RegisterData.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .password2(PASSWORD)
                .lat(LAT)
                .lon(LON)
                .build();
    }

}
