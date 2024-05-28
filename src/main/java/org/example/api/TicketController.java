package org.example.api;

import lombok.RequiredArgsConstructor;
import org.example.api.model.Flight;
import org.example.api.model.Ticket;
import org.example.central.entity.TicketEntity;
import org.example.central.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ticket", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    @GetMapping("/allTickets")
    public ResponseEntity<List<TicketEntity>> loadTickets(){
        return ResponseEntity.ok(ticketService.loadTickets());
    }

    @PostMapping("/creation")
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }
}
