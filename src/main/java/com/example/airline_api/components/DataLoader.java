package com.example.airline_api.components;

import com.example.airline_api.models.Flight;
import com.example.airline_api.models.Passenger;
import com.example.airline_api.repositories.FlightRepository;
import com.example.airline_api.repositories.PassengerRepository;
import com.example.airline_api.services.FlightService;
import com.example.airline_api.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    PassengerService passengerService;

    @Autowired
    FlightService flightService;

    public DataLoader(){
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Flight hawaiiFlight = new Flight("Hawaii", 2);
        Flight japanFlight = new Flight("Japan", 15);
        Passenger anna = new Passenger("Anna", "anna@bnta.com");
        Passenger zsolt = new Passenger("Zsolt", "zsolt@bnta.com");

        flightService.addNewFlight(hawaiiFlight);
        flightService.addNewFlight(japanFlight);

        passengerService.addNewPassenger(anna);
        passengerService.addNewPassenger(zsolt);






    }

}
