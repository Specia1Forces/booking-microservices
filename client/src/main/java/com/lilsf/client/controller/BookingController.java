package com.lilsf.client.controller;

import com.lilsf.client.dto.reservationHotelDto;
import com.lilsf.client.security.CurrentClient;
import com.lilsf.client.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/booking")
@Slf4j
public class BookingController {
    public final BookingService bookingService;
    private final CurrentClient currentClient;

    public BookingController(BookingService bookingService, CurrentClient currentClient) {
        this.bookingService = bookingService;
        this.currentClient = currentClient;
    }


    @PostMapping("/room")
    public ResponseEntity<String> reservationHotels(@RequestBody reservationHotelDto reservationHotelDto,
                                                     Principal principal) {
        int clientId = currentClient.idFor(principal.getName());
        log.info("Received reservation request for room ID: {} from client ID: {}", reservationHotelDto.getRoomId(), clientId);
        bookingService.makeReservations(clientId, reservationHotelDto.getRoomId(),
                reservationHotelDto.getStartDate(), reservationHotelDto.getEndDate());
        log.info("Room ID: {} successfully reserved for client ID: {}", reservationHotelDto.getRoomId(), clientId);
        return new ResponseEntity<>("Room is reservation successfully!", HttpStatus.OK);
    }

}
