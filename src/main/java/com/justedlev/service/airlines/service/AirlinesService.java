package com.justedlev.service.airlines.service;

import static com.justedlev.service.airlines.api.Code.*;

import com.justedlev.service.airlines.api.Code;
import com.justedlev.service.airlines.api.ServiceNamesConstants;
import com.justedlev.service.airlines.api.dto.*;
import com.justedlev.service.airlines.repository.AircraftRepository;
import com.justedlev.service.airlines.repository.AirlineRepository;
import com.justedlev.service.airlines.repository.DestinationRepository;
import com.justedlev.service.airlines.repository.DistancesRepository;
import com.justedlev.service.airlines.repository.entity.AircraftEntity;
import com.justedlev.service.airlines.repository.entity.AirlineEntity;
import com.justedlev.service.airlines.repository.entity.DestinationEntity;
import com.justedlev.service.airlines.repository.entity.DistanceEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.justedlev.service.airlines.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Qualifier(ServiceNamesConstants.AIRLINES_SERVICE_NAME)
public class AirlinesService extends AbstractAirlinesService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private DistancesRepository distancesRepository;

    @Override
    public Code addAirline(String name, double initialBudget, double latitude, double longitude) {
        AirlineEntity airline = airlineRepository.getByName(name).orElse(null);
        if (airline != null) {
            AirlineAlreadyExistsException exception = new AirlineAlreadyExistsException(
                    String.format("by name '%s'", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        addDestination(name, latitude, longitude);
        airline = new AirlineEntity(name, initialBudget, latitude, longitude);
        airlineRepository.save(airline);
        logDebugAddedToDb(name, initialBudget, latitude, longitude);
        return OK;
    }

    @Override
    public List<AirlineDTO> getAirlines() {
        return airlineRepository.findAll().stream().map(this::convertAirlineEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Code addAircraftToAirline(String aircraftSerialNumber, String airlineName) {
        AircraftEntity aircraft = aircraftRepository.getBySerialNumber(aircraftSerialNumber).orElse(null);
        if (aircraft == null) {
            AircraftNotExistsException exception = new AircraftNotExistsException(
                    String.format("by serial number '%s'", aircraftSerialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(aircraftSerialNumber);
        AirlineEntity airline = airlineRepository.getByName(airlineName).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(
                    String.format("by name '%s'", airlineName));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName);
        if (airline.getAircraftList().contains(aircraft)) {
            AlreadyExistsException exception = new AlreadyExistsException(
                    String.format("Aircraft by serial number " + "'%s' in the airline '%s' already exists",
                            aircraftSerialNumber, airlineName));
            log.debug(exception.getMessage());
            throw exception;
        }
        if (airline.getBudget() < aircraft.getPrice()) {
            IncorrectValuesException exception = new IncorrectValuesException(
                    String.format("Airline '%s' does not have enough funds to buy an aircraft '%s'", airlineName,
                            aircraftSerialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        if (aircraft.getAirline() != null) {
            sellAircraft(aircraftSerialNumber);
        }
        aircraft.setAirline(airline);
        aircraft.setDateStartUse(LocalDate.now());
        aircraftRepository.save(aircraft);
        logDebugUpdatedInDb();
        airline.setBudget(airline.getBudget() - aircraft.getPrice());
        airline.getAircraftList().add(aircraft);
        airlineRepository.save(airline);
        logDebugUpdatedInDb();
        return OK;
    }

    @Override
    public List<AircraftDTO> getAirlineAircraft(String airlineName) {
        AirlineEntity airline = airlineRepository.getByName(airlineName).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(
                    String.format("by name '%s'", airlineName));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName);
        return airline.getAircraftList().stream().map(this::convertAircraftEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Code sellAircraft(String aircraftSerialNumber) {
        AircraftEntity aircraft = aircraftRepository.getBySerialNumber(aircraftSerialNumber).orElse(null);
        if (aircraft == null) {
            AircraftNotExistsException exception = new AircraftNotExistsException(
                    String.format("by serial number '%s'", aircraftSerialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(aircraftSerialNumber);
        AirlineEntity airline = aircraft.getAirline();
        if (airline == null) {
            NotExistsException exception = new NotExistsException(
                    String.format("Aircraft '%s' has no owner yet", aircraftSerialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        if (!airline.getAircraftList().contains(aircraft)) {
            NotExistsException exception = new NotExistsException(
                    String.format("Aircraft by serial number '%s' in the airline '%s' not exists", aircraftSerialNumber,
                            airline.getName()));
            log.debug(exception.getMessage());
            throw exception;
        }
        double price = calculatePrice(aircraft.getPrice(),
                ChronoUnit.MONTHS.between(aircraft.getDateStartUse(), LocalDate.now()));
        airline.setBudget(airline.getBudget() + price);
        airline.getAircraftList().remove(aircraft);
        airlineRepository.save(airline);
        logDebugUpdatedInDb();
        aircraft.setPrice(price);
        aircraft.setAirline(null);
        aircraftRepository.save(aircraft);
        logDebugUpdatedInDb();
        return OK;
    }

    @Override
    public Code addDestination(String name, double latitude, double longitude) {
        DestinationEntity destination = destinationRepository.getByName(name).orElse(null);
        if (destination != null) {
            DestinationAlreadyExistsException exception = new DestinationAlreadyExistsException(
                    String.format("by name '%s'", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(name);
        destination = destinationRepository.getByLocation(latitude, longitude).orElse(null);
        if (destination != null) {
            DestinationAlreadyExistsException exception = new DestinationAlreadyExistsException(
                    String.format("by latitude='%s' and longitude='%s'", latitude, longitude));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(latitude, longitude);
        destination = new DestinationEntity(name, latitude, longitude);
        destinationRepository.save(destination);
        logDebugUpdatedInDb();
        return OK;
    }

    @Override
    public AirlineDestinationsDTO getAirlineDestinations(String airlineName) {
        AirlineEntity airline = airlineRepository.getByName(airlineName).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(airlineName);
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName);
        List<AirlineDestinationDTO> list = airline.getDistances().stream()
                .map(this::convertDistanceEntityToAirlineDestination).collect(Collectors.toList());
        return new AirlineDestinationsDTO(airlineName, list);
    }

    @Override
    public AirlineDestinationsDTO getAirlineDestinationsByAircraftMaxDistance(String airlineName) {
        AirlineEntity airline = airlineRepository.getByName(airlineName).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(airlineName);
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName);
        Set<DistanceEntity> list = airline.getAircraftList().stream().map(this::filterDistanceByAircraftMaxDistance)
                .flatMap(Set::stream).collect(Collectors.toSet());
        return new AirlineDestinationsDTO(airlineName,
                list.stream().map(this::convertDistanceEntityToAirlineDestination).collect(Collectors.toList()));
    }

    @Override
    public List<DestinationDTO> getDestinations() {
        return destinationRepository.findAll().stream().map(this::convertDestinationEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Code addAirlineDestination(String airlineName, String destinationName) {
        if (airlineName.equals(destinationName)) {
            IncorrectValuesException exception = new IncorrectValuesException(
                    String.format("Airline name '%s' and destination name " + "'%s' must be different", airlineName,
                            destinationName));
            log.debug(exception.getMessage());
            throw exception;
        }
        AirlineEntity airline = airlineRepository.getByName(airlineName).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(
                    String.format("by name '%s'", airlineName));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName);
        DestinationEntity destination = destinationRepository.getByName(destinationName).orElse(null);
        if (destination == null) {
            DestinationAlreadyExistsException exception = new DestinationAlreadyExistsException(
                    String.format("by name '%s'", destinationName));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(destinationName);
        double calculatedDistance = calculateDistance(airline.getLatitude(), airline.getLongitude(),
                destination.getLatitude(), destination.getLongitude());
        DistanceEntity distance = new DistanceEntity(airline, destination, calculatedDistance);
        Set<DistanceEntity> distances = airline.getDistances();
        if (distances.contains(distance)) {
            AlreadyExistsException exception = new AlreadyExistsException(String.format(
                    "Destination by name " + "'%s' in the airline '%s' already exists", destinationName, airlineName));
            log.debug(exception.getMessage());
            throw exception;
        }
        distancesRepository.save(distance);
        logDebugUpdatedInDb();
        distances.add(distance);
        airlineRepository.save(airline);
        logDebugUpdatedInDb();
        return OK;
    }

    @Override
    public Code addAircraft(String serialNumber, double aircraftPrice, int aircraftMaxDistanceInKm) {
        AircraftEntity aircraft = aircraftRepository.getBySerialNumber(serialNumber).orElse(null);
        if (aircraft != null) {
            AircraftAlreadyExistsException exception = new AircraftAlreadyExistsException(
                    String.format("by serial number '%s'", serialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        aircraft = new AircraftEntity(serialNumber, aircraftPrice, aircraftMaxDistanceInKm);
        aircraftRepository.save(aircraft);
        logDebugUpdatedInDb();
        return OK;
    }

    @Override
    public List<AircraftDTO> getAircraft() {
        return aircraftRepository.findAll().stream().map(this::convertAircraftEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Code deleteAircraft(String serialNumber) {
        AircraftEntity aircraft = aircraftRepository.getBySerialNumber(serialNumber).orElse(null);
        if (aircraft == null) {
            AircraftNotExistsException exception = new AircraftNotExistsException(
                    String.format("by serial number '%s'", serialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(serialNumber);
        aircraftRepository.delete(aircraft);
        logDebugDeletedFromDb(serialNumber);
        return OK;
    }

    @Override
    public AirlineDTO getAirline(String name) {
        AirlineEntity airline = airlineRepository.getByName(name).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(String.format("by name '%s'", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(name);
        return convertAirlineEntityToDto(airline);
    }

    @Override
    public Code deleteAirline(String name) {
        AirlineEntity airline = airlineRepository.getByName(name).orElse(null);
        if (airline == null) {
            AirlineNotExistsException exception = new AirlineNotExistsException(String.format("by name '%s'", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(name);
        airlineRepository.delete(airline);
        logDebugDeletedFromDb(name);
        return OK;
    }

    @Override
    public AircraftDTO getAircraft(String serialNumber) {
        AircraftEntity aircraft = aircraftRepository.getBySerialNumber(serialNumber).orElse(null);
        if (aircraft == null) {
            AircraftNotExistsException exception = new AircraftNotExistsException(
                    String.format("by serial number '%s'", serialNumber));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(serialNumber);
        return convertAircraftEntityToDto(aircraft);
    }

    @Override
    public DestinationDTO getDestination(double latitude, double longitude) {
        DestinationEntity destination = destinationRepository.getByLocation(latitude, longitude).orElse(null);
        if (destination == null) {
            DestinationNotExistsException exception = new DestinationNotExistsException(
                    String.format("by latitude = %s and longitude = %s", latitude, longitude));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(latitude, longitude);
        return convertDestinationEntityToDto(destination);
    }

    @Override
    public DestinationDTO getDestination(String name) {
        DestinationEntity destination = destinationRepository.getByName(name).orElse(null);
        if (destination == null) {
            DestinationNotExistsException exception = new DestinationNotExistsException(
                    String.format("by name = %s", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(name);
        return convertDestinationEntityToDto(destination);
    }

    @Override
    public Code deleteDestination(String name) {
        DestinationEntity destination = destinationRepository.getByName(name).orElse(null);
        if (destination == null) {
            DestinationNotExistsException exception = new DestinationNotExistsException(
                    String.format("by name '%s'", name));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(name);
        destinationRepository.delete(destination);
        logDebugDeletedFromDb(name);
        return OK;
    }

    @Override
    public Code deleteDestination(double latitude, double longitude) {
        return null;
    }

    @Override
    public Code deleteAirlineDestination(String airlineName, String destinationName) {
        DistanceEntity distance = distancesRepository.getByAirlineAndDestinationName(airlineName, destinationName)
                .orElse(null);
        if (distance == null) {
            DestinationNotExistsException exception = new DestinationNotExistsException(
                    String.format("from '%s' to '%s'", airlineName, destinationName));
            log.debug(exception.getMessage());
            throw exception;
        }
        logDebugReceivedFromDb(airlineName, destinationName);
        distancesRepository.delete(distance);
        logDebugDeletedFromDb(airlineName, destinationName);
        return OK;
    }

    private AirlineDTO convertAirlineEntityToDto(AirlineEntity entity) {
        return AirlineDTO.builder().name(entity.getName()).currentBalance(entity.getBudget())
                .homeBaseLocation(
                        LocationDTO.builder().latitude(entity.getLatitude()).longitude(entity.getLongitude()).build())
                .build();
    }

    private DestinationDTO convertDestinationEntityToDto(DestinationEntity entity) {
        return DestinationDTO.builder().name(entity.getName())
                .location(LocationDTO.builder().latitude(entity.getLatitude()).longitude(entity.getLongitude()).build())
                .build();
    }

    private AircraftDTO convertAircraftEntityToDto(AircraftEntity entity) {
        return AircraftDTO.builder().serialNumber(entity.getSerialNumber()).price(entity.getPrice())
                .maxDistanceKm(entity.getMaxDistanceKm()).build();
    }

    private DistanceDTO convertDistanceEntityToDto(DistanceEntity entity) {
        return DistanceDTO.builder().airline(convertAirlineEntityToDto(entity.getAirline()))
                .destination(convertDestinationEntityToDto(entity.getDestination())).distance(entity.getDistance())
                .build();
    }

    private AirlineDestinationDTO convertDistanceEntityToAirlineDestination(DistanceEntity entity) {
        return AirlineDestinationDTO.builder().destination(convertDestinationEntityToDto(entity.getDestination()))
                .distance(entity.getDistance()).build();
    }

    private Set<DistanceEntity> filterDistanceByAircraftMaxDistance(AircraftEntity aircraftEntity) {
        return aircraftEntity.getAirline().getDistances().stream()
                .filter(d -> d.getDistance() <= aircraftEntity.getMaxDistanceKm()).collect(Collectors.toSet());
    }

    private void logDebugAddedToDb(Object... values) {
        log.debug("Data added to db with values: {}", values);
    }

    private void logDebugDeletedFromDb(Object... values) {
        log.debug("Data deleted from db by values: {}", values);
    }

    private void logDebugReceivedFromDb(Object... values) {
        log.debug("Data received from db by value: {}", values);
    }

    private void logDebugUpdatedInDb() {
        log.debug("Data updated in db");
    }
}
