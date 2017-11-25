package travelcompare.restapi.api.controller;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.api.model.response.MessageResponse;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping
    public ResponseEntity<User> register(
            @RequestBody RegisterData requestBody
    ) {
        if(!requestBody.valid().isValid()) {
            return ResponseEntity.status(400).build();
        }

        if(userService.getUserByEmail(requestBody.getEmail()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        User user = userService.register(requestBody);

        return ResponseEntity.ok(user);
    }

}
