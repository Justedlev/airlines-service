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
@RequestMapping(PathConstants.AIRCRAFT_PATH)
public class AircraftController {

    @Autowired
    @Qualifier(ServiceNamesConstants.AIRLINES_SERVICE_NAME)
    private IAirlines service;

    @GetMapping
    public ResponseEntity<?> getAircraft(@RequestParam(name = "sn", required = false) String serialNumber) {
        logDegug("", serialNumber);
        if(serialNumber != null) {
            return ResponseEntity.ok(service.getAircraft(serialNumber));
        }
        return ResponseEntity.ok(service.getAircraft());
    }

    @PostMapping(PathConstants.ADD_PATH)
    public ResponseEntity<?> addAircraft(@RequestParam("sn") String serialNumber,
                                         @RequestParam double price,
                                         @RequestParam("max_distance") int maxDistanceKm) {
        logDegug(PathConstants.ADD_PATH, serialNumber, price, maxDistanceKm);
        return ResponseEntity.ok(service.addAircraft(serialNumber, price, maxDistanceKm));
    }

    @DeleteMapping(PathConstants.DELETE_PATH)
    public ResponseEntity<?> deleteAircraft(@RequestParam("sn") String serialNumber) {
        logDegug(PathConstants.DELETE_PATH, serialNumber);
        return ResponseEntity.ok(service.deleteAircraft(serialNumber));
    }

    private void logDegug(String path, Object... values) {
        log.debug("Request: '{}{}' with values: {}", PathConstants.AIRCRAFT_PATH, path, values);
    }

}
