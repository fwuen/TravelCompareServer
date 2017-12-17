package travelcompare.restapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travelcompare.restapi.api.RestURLs;
import travelcompare.restapi.api.model.request.WayData;
import travelcompare.restapi.data.model.Way;
import travelcompare.restapi.data.service.WayService;

@RestController
public class WayController {

    @Autowired
    private WayService wayService;

    @PostMapping(RestURLs.WAY_POST)
    public ResponseEntity<Way> post(
            @RequestBody WayData data
    ) {

        return null;
    }

}
