package travelcompare.restapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.WayData;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.model.Way;
import travelcompare.restapi.data.service.UserService;
import travelcompare.restapi.data.service.WayService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class WayController {

    @Autowired
    private WayService wayService;

    @Autowired
    private UserService userService;

    @PostMapping(RestURLs.WAY_POST)
    public ResponseEntity<Way> post(
            @RequestBody WayData data,
            Principal principal
    ) {
        if(!data.valid().isValid())
            return ResponseEntity.status(400).build();

        long creatorId;
        if(userService.getUserByEmail(principal.getName()).isPresent()) {
            creatorId = userService.getUserByEmail(principal.getName()).get().getId();
        } else {
            return ResponseEntity.status(401).build();
        }

        data.setCreatorId(creatorId);
        Way way = wayService.createRoute(data);

        return ResponseEntity.ok(way);
    }

    @PutMapping(RestURLs.WAY_PUT)
    public ResponseEntity<Way> put(
            @RequestBody Way way,
            Principal principal
    ) {
        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if(!loggedInUser.isPresent() || loggedInUser.get().getId() != way.getCreatorId())
            return ResponseEntity.status(403).build();

        return ResponseEntity.ok(
                wayService.updateRoute(way)
        );
    }

    @GetMapping(RestURLs.WAY_GET)
    public ResponseEntity<Way> get(
            @PathVariable("id") long id
    ) {
        Optional<Way> wayOptional = wayService.getWayById(id);

        if(!wayOptional.isPresent())
            return ResponseEntity.status(404).build();

        return ResponseEntity.ok(
                wayOptional.get()
        );
    }

}
