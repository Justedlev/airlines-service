package com.justedlev.service.airlines.service.interfaces;

import com.justedlev.service.airlines.api.Code;
import com.justedlev.service.airlines.api.dto.AircraftDTO;
import com.justedlev.service.airlines.api.dto.AirlineDTO;
import com.justedlev.service.airlines.api.dto.AirlineDestinationsDTO;
import com.justedlev.service.airlines.api.dto.DestinationDTO;

import java.util.List;

public interface IAirlines {

    Code addAirline(String name, double budget, double latitude, double longitude);

    List<AirlineDTO> getAirlines();

    Code addAircraftToAirline(String aircraftSerialNumber, String airlineName);

    List<AircraftDTO> getAirlineAircraft(String airlineName);

    Code sellAircraft(String aircraftSerialNumber);

    Code addDestination(String name, double latitude, double longitude);

    AirlineDestinationsDTO getAirlineDestinations(String airlineName);

    AirlineDestinationsDTO getAirlineDestinationsByAircraftMaxDistance(String airlineName);

    List<DestinationDTO> getDestinations();

    Code addAirlineDestination(String airlineName, String destinationName);

    Code addAircraft(String serialNumber, double aircraftPrice, int aircraftMaxDistanceInKm);

    AircraftDTO getAircraft(String aircraftSerialNumber);

    List<AircraftDTO> getAircraft();

    Code deleteAircraft(String serialNumber);

    AirlineDTO getAirline(String name);

    Code deleteAirline(String name);

    DestinationDTO getDestination(double latitude, double longitude);

    DestinationDTO getDestination(String name);

    Code deleteDestination(String name);

    Code deleteDestination(double latitude, double longitude);

    Code deleteAirlineDestination(String airlineName, String destinationName);

}
