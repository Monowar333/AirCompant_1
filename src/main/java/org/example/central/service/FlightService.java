package org.example.central.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.api.model.Flight;
import org.example.central.entity.FlightEntity;
import org.example.central.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportService airportService;

    @Transactional(readOnly = true)
    public List<FlightEntity> loadFlight() {
       return flightRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FlightEntity loadFlightById(Long id) {
        return flightRepository.findById(id).get();
    }

    @Transactional
    public ResponseEntity<Map<String, String>> createFlight(Flight flight) {

        Map<String, String> validation = flightValidation(flight);

        if (!validation.isEmpty()) {
            return new ResponseEntity<>(validation, HttpStatus.NOT_ACCEPTABLE);
        }

        FlightEntity flightEntity = new FlightEntity();

        if (flight.getId() != null) {
            flightEntity = flightRepository.findById(flight.getId()).orElseThrow(EntityNotFoundException::new);
        }

        setFieldIfPresent(flight.getOrigin(), airportService::findById, flightEntity::setOrigin);
        setFieldIfPresent(flight.getDestination(), airportService::findById, flightEntity::setDestination);

        if (flight.getFlightNumber() != null) {
            flightEntity.setNumber(flight.getFlightNumber());
        }

        if (flight.getEta() != null) {
            flightEntity.setEta(flight.getEta());
        }

        if (flight.getEtd() != null) {
            flightEntity.setEtd(flight.getEtd());
        }

        if (flight.getStatus() != null) {
            flightEntity.setStatus(flight.getStatus());
        }

        flightRepository.save(flightEntity);
        return ResponseEntity.ok(Collections.emptyMap());
    }
    private <T> void setFieldIfPresent(Long id, Function<Long, Optional<T>> findById, Consumer<T> setter) {
        Optional.ofNullable(id)
                .map(findById)
                .ifPresent(e -> e.ifPresent(setter));
    }

    private Map<String, String> flightValidation(Flight flight) {
        Map<String, String> errors = new HashMap<>();

        //частина обов'язкових філдів перевіряємо що заповненно і данні валідні
        if (StringUtils.isBlank(flight.getFlightNumber())) {
            errors.put(Flight.Fields.flightNumber, "Номер обов'язкова");
        }

        if (flight.getDestination() == null || airportService.findById(flight.getDestination()).isEmpty()) {
            errors.put(Flight.Fields.destination, "Місце Приюуття обов'язкова");
        }

        if (flight.getStatus() == null) {
            errors.put(Flight.Fields.status, "Статус обов'язкова");
        }

        if (flight.getOrigin() == null || airportService.findById(flight.getOrigin()).isEmpty()) {
            errors.put(Flight.Fields.origin, "Місце Вдправки обов'язкова");
        }

        if (flight.getEtd() == null) {
            errors.put(Flight.Fields.etd, "Час Вдправки обов'язкова");
        }

        if (flight.getEta() == null) {
            errors.put(Flight.Fields.eta, "Час Прибуття обов'язкова");
        }

        if (flight.getEta() != null && flight.getEtd() != null
            && flight.getEta() < flight.getEtd()) {
            errors.put(Flight.Fields.etd, "Час відправки має юути раніше часу прибуття");
        }

        return errors;
    }
}
