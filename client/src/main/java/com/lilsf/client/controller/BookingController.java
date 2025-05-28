package com.lilsf.client.controller;

import com.lilsf.client.dto.SearchHotelDto;
import com.lilsf.client.dto.reservationHotelDto;
import com.lilsf.client.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/booking")
@Slf4j
public class BookingController {
    public final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/room")
    public ResponseEntity<String> reservationHotels(@RequestBody reservationHotelDto reservationHotelDto) {
        int clientId = 1;
        log.info("Received reservation request for room ID: {} from client ID: {}", reservationHotelDto.getRoomId(), clientId);
        bookingService.makeReservations(clientId, reservationHotelDto.getRoomId(), reservationHotelDto.getStartDate(), reservationHotelDto.getStartDate());
        log.info("Room ID: {} successfully reserved for client ID: {}", reservationHotelDto.getRoomId(), clientId);
        return new ResponseEntity<>("Room is reservation successfully!", HttpStatus.OK);
    }

}
