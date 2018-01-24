package travelcompare.restapi.api.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.RouteInfoData;
import travelcompare.restapi.data.model.RouteInfo;
import travelcompare.restapi.data.model.User;
import travelcompare.restapi.data.service.RouteInfoService;
import travelcompare.restapi.data.service.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

//TODO: nochmal über Statuscodes schauen
@RestController
public class RouteInfoController {

    @Autowired
    private RouteInfoService routeInfoService;

    @Autowired
    private UserService userService;

    @PostMapping(RestURLs.ROUTE_INFO_POST)
    public ResponseEntity<RouteInfo> post(
            @RequestBody RouteInfoData data,
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
        RouteInfo routeInfo = routeInfoService.createRoute(data);

        return ResponseEntity.ok(routeInfo);
    }

    @PutMapping(RestURLs.ROUTE_INFO_PUT)
    public ResponseEntity<RouteInfo> put(
            @RequestBody RouteInfo routeInfo,
            Principal principal
    ) {
        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if (!loggedInUser.isPresent() || loggedInUser.get().getId() != routeInfo.getCreatorId())
            return ResponseEntity.status(403).build();

        return ResponseEntity.ok(
                routeInfoService.updateRoute(routeInfo)
        );
    }

    @GetMapping(RestURLs.ROUTE_INFO_GET)
    public ResponseEntity<RouteInfo> get(
            @PathVariable("id") long id
    ) {
        Optional<RouteInfo> routeInfoOptional = routeInfoService.getRouteInfoById(id);

        if (!routeInfoOptional.isPresent())
            return ResponseEntity.status(404).build();

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(routeInfoOptional.get());
    }

    @GetMapping(RestURLs.ROUTE_INFOS_GET)
    public ResponseEntity<List<RouteInfo>> get(
            Principal principal
    ) {
        if (!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        List<RouteInfo> routeInfos = routeInfoService.getRouteInfosByCreatorId(loggedInUser.get().getId());

        if (!(routeInfos.size() > 0))
            return ResponseEntity.status(404).build();



        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(routeInfos);
    }

    @Transactional
    @DeleteMapping(RestURLs.ROUTE_INFO_DELETE)
    public ResponseEntity<Void> delete(
            @PathVariable("id") long id,
            Principal principal
    ) {
        if (!userService.userExistsByEmail(principal.getName()))
            return ResponseEntity.status(401).build();

        Optional<User> loggedInUser = userService.getUserByEmail(principal.getName());

        if (!loggedInUser.isPresent())
            return ResponseEntity.status(403).build();

        Optional<RouteInfo> routeInfoToDelete = routeInfoService.getRouteInfoById(id);

        if (!routeInfoToDelete.isPresent())
            return ResponseEntity.status(400).build();

        if (!(routeInfoToDelete.get().getCreatorId() == loggedInUser.get().getId()))
            return ResponseEntity.status(403).build();

        routeInfoService.deleteRouteInfoById(id);

        return ResponseEntity.ok().build();

    }
}
