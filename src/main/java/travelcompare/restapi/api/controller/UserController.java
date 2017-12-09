package travelcompare.restapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.ChangePasswordData;
import travelcompare.restapi.api.model.request.RegisterData;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.service.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping(RestURLs.USER_REGISTER)
    public ResponseEntity<User> register(
            @RequestBody RegisterData data
    ) {
        if(!data.valid().isValid())
            return ResponseEntity.status(400).build();

        if(userService.getUserByEmail(data.getEmail()).isPresent())
            return ResponseEntity.status(409).build();

        User user = userService.register(data);

        return ResponseEntity.ok(user);
    }

    @GetMapping(RestURLs.USER_GET)
    public ResponseEntity<User> get(
            @PathVariable("id") long id
    ) {
        Optional<User> userOptional = userService.getUserByID(id);

        if(!userOptional.isPresent())
            return ResponseEntity.status(404).build();

        return ResponseEntity.ok(userOptional.get());
    }

    @PutMapping(RestURLs.USER_PUT)
    public ResponseEntity<User> put(
            @RequestBody User user,
            Principal principal
    ) {
        if(!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        if(!userService.userExistsByID(user.getId()))
            return ResponseEntity.status(404).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != user.getId())
            return ResponseEntity.status(403).build();

        return ResponseEntity.ok(
                userService.updateUser(user)
        );
    }

    @DeleteMapping(RestURLs.USER_DELETE)
    public ResponseEntity<Void> delete(
            Principal principal
    ) {
        if(!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if(!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        userService.deleteUserByEmail(principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping(RestURLs.USER_CHANGE_PASSWORD)
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordData data,
            Principal principal
    ) {
        if(!data.valid().isValid())
            return ResponseEntity.status(400).build();

        if(!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if(!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        userService.changePassword(loggedInUser.get(), data);

        return ResponseEntity.ok().build();
    }

}