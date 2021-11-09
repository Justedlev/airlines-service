package com.justedlev.service.airlines.rest;

import com.justedlev.service.airlines.api.PathConstants;
import com.justedlev.service.airlines.api.ServiceNamesConstants;
import com.justedlev.service.airlines.service.interfaces.IAirlines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(PathConstants.DESTINATIONS_PATH)
public class DestinationsController {

    @Autowired
    @Qualifier(ServiceNamesConstants.AIRLINES_SERVICE_NAME)
    private IAirlines service;

    @GetMapping
    public ResponseEntity<?> getDestinations(@RequestParam(required = false) String name) {
        logDegug("", name);
        if (name != null) {
            return ResponseEntity.ok(service.getDestination(name));
        }
        return ResponseEntity.ok(service.getDestinations());
    }

    @PostMapping(PathConstants.ADD_PATH)
    public ResponseEntity<?> addDestination(@RequestParam String name, @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude) {
        logDegug(PathConstants.ADD_PATH, name, latitude, longitude);
        return ResponseEntity.ok(service.addDestination(name, latitude, longitude));
    }

    @DeleteMapping(PathConstants.DELETE_PATH)
    public ResponseEntity<?> deleteDestination(@RequestParam String name) {
        logDegug(PathConstants.DELETE_PATH, name);
        return ResponseEntity.ok(service.deleteDestination(name));
    }

    private void logDegug(String path, Object... values) {
        log.debug("Request: '{}{}' with values: {}", PathConstants.DESTINATIONS_PATH, path, values);
    }

}
