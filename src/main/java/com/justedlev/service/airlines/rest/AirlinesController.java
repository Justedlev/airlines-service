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
@RequestMapping(PathConstants.AIRLINES_PATH)
public class AirlinesController {

    @Autowired
    @Qualifier(ServiceNamesConstants.AIRLINES_SERVICE_NAME)
    private IAirlines service;

    @GetMapping
    public ResponseEntity<?> getAirline(@RequestParam(required = false) String name) {
        logDegug("", name);
        if (name != null) {
            return ResponseEntity.ok(service.getAirline(name));
        }
        return ResponseEntity.ok(service.getAirlines());
    }

    @PostMapping(PathConstants.ADD_PATH)
    public ResponseEntity<?> addAirline(@RequestParam String name, @RequestParam double budget,
            @RequestParam("lat") double latitude, @RequestParam("lon") double longitude) {
        logDegug(PathConstants.ADD_PATH, name, budget, latitude, longitude);
        return ResponseEntity.ok(service.addAirline(name, budget, latitude, longitude));
    }

    @GetMapping(PathConstants.AIRCRAFT_PATH)
    public ResponseEntity<?> getAirlineAircraft(@RequestParam("airline_name") String airlineName) {
        logDegug(PathConstants.AIRCRAFT_PATH, airlineName);
        return ResponseEntity.ok(service.getAirlineAircraft(airlineName));
    }

    @PutMapping(PathConstants.AIRCRAFT_ADD_PATH)
    public ResponseEntity<?> addAircraftToAirline(@RequestParam("aircraft_sn") String serialNumber,
            @RequestParam("airline_name") String airlineName) {
        logDegug(PathConstants.AIRCRAFT_ADD_PATH, serialNumber, airlineName);
        return ResponseEntity.ok(service.addAircraftToAirline(serialNumber, airlineName));
    }

    @PutMapping(PathConstants.AIRCRAFT_SELL_PATH)
    public ResponseEntity<?> sellAircraft(@RequestParam("aircraft_sn") String serialNumber) {
        logDegug(PathConstants.SELL_PATH, serialNumber);
        return ResponseEntity.ok(service.sellAircraft(serialNumber));
    }

    @PutMapping(PathConstants.DESTINATIONS_ADD_PATH)
    public ResponseEntity<?> addAirlineDestination(@RequestParam("airline_name") String airlineName,
            @RequestParam("destination_name") String destinationName) {
        logDegug(PathConstants.DESTINATIONS_ADD_PATH, airlineName, destinationName);
        return ResponseEntity.ok(service.addAirlineDestination(airlineName, destinationName));
    }

    @GetMapping(PathConstants.DESTINATIONS_ALL_PATH)
    public ResponseEntity<?> getAirlineDestinations(@RequestParam("airline_name") String airlineName) {
        logDegug(PathConstants.DESTINATIONS_PATH, airlineName);
        return ResponseEntity.ok(service.getAirlineDestinations(airlineName));
    }

    @GetMapping(PathConstants.DESTINATIONS_PATH)
    public ResponseEntity<?> getAirlineDestinationsByAircraft(
            @RequestParam("airline_name") String airlineName) {
        logDegug(PathConstants.DESTINATIONS_ALL_PATH, airlineName);
        return ResponseEntity.ok(service.getAirlineDestinationsByAircraftMaxDistance(airlineName));
    }

    @DeleteMapping(PathConstants.DELETE_PATH)
    public ResponseEntity<?> deleteAirline(@RequestParam("airline_name") String airlineName) {
        logDegug(PathConstants.DELETE_PATH, airlineName);
        return ResponseEntity.ok(service.deleteAirline(airlineName));
    }

    @DeleteMapping(PathConstants.DESTINATIONS_DELETE_PATH)
    public ResponseEntity<?> deleteAirlineDestination(@RequestParam("airline_name") String airlineName,
            @RequestParam("destination_name") String destinationName) {
        logDegug(PathConstants.DESTINATIONS_DELETE_PATH, airlineName, destinationName);
        return ResponseEntity.ok(service.deleteAirlineDestination(airlineName, destinationName));
    }

    private void logDegug(String path, Object... values) {
        log.debug("Request: '{}{}' with values: {}", PathConstants.AIRLINES_PATH, path, values);
    }

}
