package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.model.Flight;
import org.example.api.model.FlightSearch;
import org.example.central.entity.FlightEntity;
import org.example.central.service.FlightSearchService;
import org.example.central.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/flight", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    private final FlightSearchService flightSearch;

    @GetMapping("/allFlight")
    public ResponseEntity<List<FlightEntity>> loadFlights(){
        return ResponseEntity.ok(flightService.loadFlight());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightEntity> loadById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(flightService.loadFlightById(id));
    }

    @GetMapping("/search")
    public List<FlightEntity> searchFlight(@ModelAttribute FlightSearch search){
        return flightSearch.searchFlight(search);
    }

    @PostMapping("/creation")
    public ResponseEntity<Map<String, String>> createFlight(@RequestBody Flight flight){
        return flightService.createFlight(flight);
    }
}
