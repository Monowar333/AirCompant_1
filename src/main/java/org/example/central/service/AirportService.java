package org.example.central.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.central.entity.AirportEntity;
import org.example.central.entity.FlightEntity;
import org.example.central.repository.AirPortRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirPortRepository airPortRepository;

    @Transactional(readOnly = true)
    public List<AirportEntity> loadAirports() {
       return airPortRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AirportEntity> findById(@NonNull Long id) {
        return airPortRepository.findById(id);
    }

    public List<String> getCountries() {
        return airPortRepository.findAllCountries();
    }
}
