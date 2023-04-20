package com.example.airline_api.services;

import com.example.airline_api.models.Flight;
import com.example.airline_api.models.Passenger;
import com.example.airline_api.repositories.FlightRepository;
import com.example.airline_api.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    public Flight addNewFlight(Flight flight){
        flight.setDepartureDate(LocalDate.now().plusMonths(1));
        flight.setDepartureTime(LocalTime.now());
        return flightRepository.save(flight);
    }

    public List<Flight> findAllFlights(){
        return flightRepository.findAll();
    }

    public Flight findFlightById(Long flightId){
        return flightRepository.findById(flightId).get();
    }

    public Passenger bookPassenger(Passenger passenger, Long flightId){
        Flight flight = flightRepository.findById(flightId).get();
        flight.addPassenger(passenger);
        return passengerRepository.save(passenger);
    }

    public void cancelFlight(Long id){
        Flight flight = flightRepository.findById(id).get();
        for (Passenger passenger : flight.getPassengers()){
            passenger.removeFlight(flight);
            passengerRepository.save(passenger);
        }
        flightRepository.deleteById(id);
    }

    public List<Flight> findFlightsByDestination(String destination){
        return flightRepository.findByDestination(destination);
    }



}
