package travelcompare.restapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.WAY_TYPE;
import travelcompare.restapi.api.model.request.WayData;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.model.Way;
import travelcompare.restapi.data.service.UserService;
import travelcompare.restapi.data.service.WayService;
import travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE;
import travelcompare.restapi.logic.CheapestWayProvider;
import travelcompare.restapi.logic.FastestWayProvider;
import travelcompare.restapi.provider.model.Geo;

import javax.xml.ws.Response;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

//TODO: nochmal über Statuscodes schauen
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

    @DeleteMapping(RestURLs.WAY_DELETE)
    public ResponseEntity<Void> delete(
            @PathVariable("id") long id,
            Principal principal
    ) {
        if(!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if(!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        Optional<Way> wayToDelete = wayService.getWayById(id);

        if(!wayToDelete.isPresent())
            return ResponseEntity.status(400).build();

        if(!(wayToDelete.get().getCreatorId() == loggedInUser.get().getId()))
            return ResponseEntity.status(403).build();

        wayService.deleteWayById(id);

        return ResponseEntity.ok().build();

    }

    @GetMapping(RestURLs.WAY_FIND)
    public ResponseEntity<travelcompare.restapi.provider.model.Way> find(
            @PathVariable("start_lat") double start_lat,
            @PathVariable("start_lon") double start_lon,
            @PathVariable("dest_lat") double dest_lat,
            @PathVariable("dest_lon") double dest_lon,
            @PathVariable("date") String date,
            @PathVariable(value = "radius", required = false) int radius,
            @PathVariable(value = "fuel_type", required = false) String fuel_type,
            @PathVariable(value = "way_category", required = false) String way_category,
            Principal principal
    ) {

        CheapestWayProvider cheapestWayProvider = new CheapestWayProvider();
        FastestWayProvider fastestWayProvider = new FastestWayProvider();

        if(!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        Geo start = new Geo(start_lat, start_lon);
        Geo dest = new Geo(dest_lat, dest_lon);

        FUEL_TYPE fuelType = null;
        WAY_TYPE wayType = null;

        if (fuel_type == null) {
            fuelType = FUEL_TYPE.all;
        } else {
            try {
                fuelType = FUEL_TYPE.valueOf(fuel_type);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).build();
            }
        }

        if (way_category == null) {
            wayType = WAY_TYPE.FASTEST;
        } else {
            try {
                wayType = WAY_TYPE.valueOf(way_category);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).build();
            }
        }

        Date formatted_date = new Date(date);

        Optional<travelcompare.restapi.provider.model.Way> returnWay = null;
        if (wayType.equals(WAY_TYPE.CHEAPEST)) {
            try {
                returnWay = cheapestWayProvider.find(start, dest, radius, formatted_date, fuelType);
            } catch (Exception e) {
                return ResponseEntity.status(500).build();
            }
        }

        return ResponseEntity.ok(returnWay.get());
    }
}
