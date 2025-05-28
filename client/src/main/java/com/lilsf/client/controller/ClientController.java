package com.lilsf.client.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import com.lilsf.client.dto.BookingForClientDto;
import com.lilsf.client.models.Booking;
import com.lilsf.client.service.ClientService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.lilsf.client.controller.mapper.BookingForClientMapper.mapBookingToBookingForClientDto;
import static com.lilsf.client.controller.mapper.RoomForClientMapper.mapRoomToRoomForClientDto;

@RestController
@RequestMapping("/api/client")
@Slf4j
public class ClientController {
    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping("/{clientId}/reservations")

    public ResponseEntity<List<BookingForClientDto>> findReservations(@PathVariable int clientId) { //List<BookingForClientDto>
        log.info("Received request to find reservations for client with ID: {}", clientId);
        List<BookingForClientDto> bookings = mapBookingToBookingForClientDto(clientService.findReservations(clientId));
        if (!bookings.isEmpty()) {
            log.info("Found {} reservations for client with ID: {}", bookings.size(), clientId);
            return ResponseEntity.ok(bookings);
        } else {
            log.info("No reservations found for client with ID: {}", clientId);
            return ResponseEntity.notFound().build();
        }
    }


}
