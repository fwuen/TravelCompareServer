package travelcompare.restapi.api.controller;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping
    public ResponseEntity<User> register(
            @RequestBody RegisterData body
    ) {
        Validation validation = body.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());

        User user = userService.register(body);

        return ResponseEntity.ok(user);
    }

}
