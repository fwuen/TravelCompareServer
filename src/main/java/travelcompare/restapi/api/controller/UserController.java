package travelcompare.restapi.api.controller;

import com.google.common.base.Preconditions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.User;

@RestController
public class UserController {

    @PostMapping
    public ResponseEntity<User> register(
            @RequestBody RegisterData body
    ) {
        Validation validation = body.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());

        return ResponseEntity.ok(null);
    }

}
