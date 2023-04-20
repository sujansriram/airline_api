package com.example.airline_api.controllers;

import com.example.airline_api.models.Flight;
import com.example.airline_api.models.Passenger;
import com.example.airline_api.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight){
        return new ResponseEntity<>(flightService.addNewFlight(flight), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> displayAllFlights(@RequestParam Optional<String> destination){
        if (destination.isPresent()){
            try {
                return new ResponseEntity<>(flightService.findFlightsByDestination(destination.get()), HttpStatus.OK);
            } catch (NoSuchElementException e){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(flightService.findAllFlights(), HttpStatus.OK);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Flight> displaySpecificFlight(@PathVariable Long id){
        try {
            return new ResponseEntity<>(flightService.findFlightById(id), HttpStatus.OK);
        } catch(NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Passenger> bookPassenger(@RequestBody Passenger passenger, @PathVariable Long id){
        Flight flight =  flightService.findFlightById(id);
        if(flight.getCapacity() == flight.getPassengers().size()){
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            return new ResponseEntity<>(flightService.bookPassenger(passenger, id), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> cancelFlight(@PathVariable Long id){
        try {
            flightService.cancelFlight(id);
            return new ResponseEntity<>(String.format("flight of id %s has been cancelled", id), HttpStatus.OK);
        } catch(NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



}
