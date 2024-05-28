package org.example.central.service;

import lombok.RequiredArgsConstructor;
import org.example.api.model.Ticket;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.TicketEntity;
import org.example.central.entity.UserEntity;
import org.example.central.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FlightService flightService;
    private final UserService userService;


    @Transactional(readOnly = true)
    public List<TicketEntity> loadTickets() {
        UserEntity user = userService.loadCurrentUser();
       return ticketRepository.allTicketsForUser(user.getId());
    }

    @Transactional
    public ResponseEntity<Map<String, String>> createTicket(Ticket ticket) {
        TicketEntity ticketEntity = new TicketEntity();

        FlightEntity flightEntity = flightService.loadFlightById(ticket.getFlightId());

        if (flightEntity == null) {
            throw new EntityNotFoundException();
        }

        ticketEntity.setFlight(flightEntity);
        ticketEntity.setPlace(ticket.getPlace());
        ticketEntity.setNote("Not Implemented Yet");
        ticketEntity.setUser(userService.loadCurrentUser());

        ticketRepository.save(ticketEntity);
        return ResponseEntity.ok(Collections.emptyMap());
    }
}
