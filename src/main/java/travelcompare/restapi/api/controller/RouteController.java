package travelcompare.restapi.api.controller;

import com.google.maps.errors.ApiException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.RouteType;
import travelcompare.restapi.data.service.UserService;
import travelcompare.restapi.external.tankerkoenig.response.FuelType;
import travelcompare.restapi.logic.RouteProvider;
import travelcompare.restapi.provider.model.Geo;
import travelcompare.restapi.provider.model.Route;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class RouteController {

    @Autowired
    private UserService userService;

    @GetMapping(RestURLs.ROUTE_FIND)
    public ResponseEntity<Route> find(
            @RequestParam("start_lat") double start_lat,
            @RequestParam("start_lon") double start_lon,
            @RequestParam("dest_lat") double dest_lat,
            @RequestParam("dest_lon") double dest_lon,
            @RequestParam("date") String date,
            @RequestParam(value = "radius", required = false) Integer radius,
            @RequestParam(value = "fuel_type", required = false) String fuel_type,
            @RequestParam(value = "route_category", required = false) String route_category,
            Principal principal
    ) throws Exception {

        RouteProvider routeProvider = new RouteProvider();

        if (!userService.userExistsByEmail(principal.getName())) {
            return ResponseEntity.status(401).build();
        }

        FuelType fuelType = FuelType.ALL;
        int rad = 50000;
        RouteType routeType = RouteType.FASTEST;

        if (radius != null) {
            rad = radius;
        }

        if (fuel_type == null) {
            fuelType = FuelType.ALL;
        } else if (fuel_type.equals("e10")) {
            fuelType = FuelType.E10;
        } else if (fuel_type.equals("e5")) {
            fuelType = FuelType.E5;
        } else if (fuel_type.equals("diesel")) {
            fuelType = FuelType.DIESEL;
        }

        if (route_category == null) {
            routeType = RouteType.FASTEST;
        } else if (route_category.equals("cheapest")) {
            routeType = RouteType.CHEAPEST;
        }

        Geo start = new Geo(start_lat, start_lon);
        Geo dest = new Geo(dest_lat, dest_lon);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date formatted_date = null;
        try {
            formatted_date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
        try {
            return ResponseEntity.ok(routeProvider.find(start, dest, rad, formatted_date, fuelType, routeType).get());
        } catch (UnirestException | ApiException e) {
            return ResponseEntity.status(503).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
