package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.central.entity.AirportEntity;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.TicketEntity;
import org.example.central.service.AirportService;
import org.example.central.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/airports", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AirPortController {

    private final AirportService airportService;
    @GetMapping("/all")
    public ResponseEntity<List<AirportEntity>> loadTickets(){
        return ResponseEntity.ok(airportService.loadAirports());
    }

    @GetMapping("/country")
    public ResponseEntity<List<String>> suggestion(){
        return ResponseEntity.ok(airportService.getCountries());
    }
}
