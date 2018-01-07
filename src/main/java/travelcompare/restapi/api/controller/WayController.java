package travelcompare.restapi.api.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static travelcompare.restapi.api.model.request.WAY_TYPE.CHEAPEST;
import static travelcompare.restapi.external.tankerkoenig.response.FUEL_TYPE.e10;

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
        if (!data.valid().isValid())
            return ResponseEntity.status(400).build();

        long creatorId;
        if (userService.getUserByEmail(principal.getName()).isPresent()) {
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

        if (!loggedInUser.isPresent() || loggedInUser.get().getId() != way.getCreatorId())
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

        if (!wayOptional.isPresent())
            return ResponseEntity.status(404).build();

        return ResponseEntity.ok(
                wayOptional.get()
        );
    }

    @GetMapping(RestURLs.WAYS_GET)
    public ResponseEntity<List<Way>> get(
            Principal principal
    ) {
        if (!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        List<Optional<Way>> ways = wayService.getWaysByCreatorId(loggedInUser.get().getId());

        if (!(ways.size() > 0))
            return ResponseEntity.status(404).build();

        for (Optional<Way> way : ways) {
            if (!way.isPresent())
                return ResponseEntity.status(404).build();
        }

        List<Way> finalWays = Lists.newArrayList();
        for (Optional<Way> way : ways) {
            finalWays.add(way.get());
        }

        return ResponseEntity.ok(
                finalWays
        );
    }

    @DeleteMapping(RestURLs.WAY_DELETE)
    public ResponseEntity<Void> delete(
            @PathVariable("id") long id,
            Principal principal
    ) {
        if (!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        Optional<Way> wayToDelete = wayService.getWayById(id);

        if (!wayToDelete.isPresent())
            return ResponseEntity.status(400).build();

        if (!(wayToDelete.get().getCreatorId() == loggedInUser.get().getId()))
            return ResponseEntity.status(403).build();

        wayService.deleteWayById(id);

        return ResponseEntity.ok().build();

    }

    @GetMapping(RestURLs.WAY_FIND)
    public ResponseEntity<travelcompare.restapi.provider.model.Way> find(
            @RequestParam("start_lat") double start_lat,
            @RequestParam("start_lon") double start_lon,
            @RequestParam("dest_lat") double dest_lat,
            @RequestParam("dest_lon") double dest_lon,
            @RequestParam("date") String date,
            @RequestParam(value = "radius", required = false) Integer radius,
            @RequestParam(value = "fuel_type", required = false) String fuel_type,
            @RequestParam(value = "way_category", required = false) String way_category,
            Principal principal
    ) {

        CheapestWayProvider cheapestWayProvider = new CheapestWayProvider();
        FastestWayProvider fastestWayProvider = new FastestWayProvider();

        if (!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        Geo start = new Geo(start_lat, start_lon);
        Geo dest = new Geo(dest_lat, dest_lon);


        // 2008-09-09
        LocalDate localDate = LocalDate.of(Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10)));

        Date formatted_date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            return ResponseEntity.ok(cheapestWayProvider.find(start, dest, 50000, formatted_date, FUEL_TYPE.all).get()) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
