package travelcompare.restapi.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DateController {

    @GetMapping("/date")
    public ResponseEntity<Date> date() {
        return ResponseEntity.ok(new Date());
    }

}
