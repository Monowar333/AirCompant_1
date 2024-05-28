package org.example.central.repository;

import org.example.central.entity.AirportEntity;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirPortRepository extends JpaRepository<AirportEntity, Long> {

    @Query(
            value = "SELECT DISTINCT country FROM airport ORDER BY country", nativeQuery = true
    )
    List<String> findAllCountries();
}
