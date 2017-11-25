package travelcompare.restapi.api.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class LoginUser {

    private String username;
    private String password;

}