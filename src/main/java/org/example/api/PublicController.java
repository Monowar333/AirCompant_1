package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.model.FlightSearch;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.UserEntity;
import org.example.central.service.AirportService;
import org.example.central.service.FlightSearchService;
import org.example.central.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "public/", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PublicController {


    private final FlightSearchService flightSearch;

    private final AirportService airportService;

    private final UserService userService;

    @GetMapping("/search")
    public List<FlightEntity> searchFlight(@ModelAttribute FlightSearch search){
        return flightSearch.searchFlight(search);
    }

    @GetMapping("/currentRole")
    public String currentRole(){
        UserEntity user = userService.loadCurrentUser();
        if (user == null) {
            return "NOT_AUTHORIZED";
        }
        return user.getRole();
    }

    @GetMapping("/country")
    public ResponseEntity<List<String>> suggestion(){
        return ResponseEntity.ok(airportService.getCountries());
    }

}
