package com.lilsf.client.controller;

import com.lilsf.client.dto.BookingForClientDto;
import com.lilsf.client.security.CurrentClient;
import com.lilsf.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static com.lilsf.client.controller.mapper.BookingForClientMapper.mapBookingToBookingForClientDto;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController {
    public final ClientService clientService;
    private final CurrentClient currentClient;

    @Autowired
    public ClientController(ClientService clientService, CurrentClient currentClient) {
        this.clientService = clientService;
        this.currentClient = currentClient;
    }



    @GetMapping("/{clientId}/reservations")

    public ResponseEntity<List<BookingForClientDto>> findReservations(@PathVariable int clientId, Principal principal) {
        int authenticatedClientId = currentClient.idFor(principal.getName());
        if (authenticatedClientId != clientId) {
            throw new AccessDeniedException("Booking access denied");
        }
        List<BookingForClientDto> bookings = mapBookingToBookingForClientDto(
                clientService.findReservations(authenticatedClientId));
        if (!bookings.isEmpty()) {
            log.info("Found {} reservations for client with ID: {}", bookings.size(), clientId);
            return ResponseEntity.ok(bookings);
        } else {
            log.info("No reservations found for client with ID: {}", clientId);
            return ResponseEntity.notFound().build();
        }
    }


}
