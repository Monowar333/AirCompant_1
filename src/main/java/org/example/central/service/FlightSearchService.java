package org.example.central.service;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.example.api.model.FlightSearch;
import org.example.central.entity.AirportEntity_;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.FlightEntity_;
import org.example.central.repository.FlightRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSearchService {
   
    private final FlightRepository flightRepository;
   
    public List<FlightEntity> searchFlight(FlightSearch flightSearch) {
        return ImmutableList.copyOf(flightRepository.findAll(buildSearchCriteria(flightSearch),
                PageRequest.of(0, 100)));
    }

    private Specification<FlightEntity> buildSearchCriteria(FlightSearch flightSearch) {
        return Specification.where((Specification<FlightEntity>) ((root, query, cb) -> cb.isTrue(cb.literal(true))))
                .and(buildOriginCountry(flightSearch))
                .and(buildDestinationCountry(flightSearch))
                .and(buildEta(flightSearch))
                .and(buildEtd(flightSearch))
                .and(buildStatus(flightSearch));
    }

    protected Specification<FlightEntity> buildOriginCountry(FlightSearch flightSearch) {
        if (flightSearch.getOrigin() == null) {
            return null;
        }

        return extracted(FlightEntity_.ORIGIN, flightSearch.getOrigin());
    }

    protected Specification<FlightEntity> buildDestinationCountry(FlightSearch flightSearch) {
        if (flightSearch.getDestination() == null) {
            return null;
        }

        return extracted(FlightEntity_.DESTINATION, flightSearch.getDestination());
    }

    protected Specification<FlightEntity> buildStatus(FlightSearch flightSearch) {
        if (flightSearch.getStatus() == null) {
            return null;
        }

        return (root, query, cb) -> {
            Predicate locationPredicate =
                    root.get("status").in(flightSearch.getStatus());
            return cb.and(locationPredicate);
        };
    }

    private static Specification<FlightEntity> extracted(String field, String value) {
        return (root, query, cb) -> {
            Predicate locationPredicate =
                    root.get(field).get(AirportEntity_.COUNTRY).in(value);
            return cb.and(locationPredicate);
        };
    }

    protected Specification<FlightEntity> buildEta(FlightSearch search) {
        if (search.getEta() == null) {
            return null;
        }

        return (root, query, cb) -> {
            Predicate categoryPredicate =
                    cb.le(root.get(FlightEntity_.ETA), (search.getEta()));
            return cb.and(categoryPredicate);
        };
    }

    protected Specification<FlightEntity> buildEtd(FlightSearch search) {
        if (search.getEtd() == null) {
            return null;
        }

        return (root, query, cb) -> {
            Predicate categoryPredicate =
                    cb.ge(root.get(FlightEntity_.ETD), (search.getEtd()));
            return cb.and(categoryPredicate);
        };
    }
}
