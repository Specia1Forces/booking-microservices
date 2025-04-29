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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.lilsf.client.controller.mapper.BookingForClientMapper.mapBookingToBookingForClientDto;
import static com.lilsf.client.controller.mapper.RoomForClientMapper.mapRoomToRoomForClientDto;

@Controller
@RequestMapping("/api/client")
public class ClientController {
    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }



    @GetMapping("/{clientId}/reservations")
    /*
    @Operation(summary = "Gets customer by ID", description = "Customer must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservations",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BookingForClientDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })


     */

    public ResponseEntity<List<BookingForClientDto>> findReservations(@PathVariable int clientId) { //List<BookingForClientDto>
        List<BookingForClientDto> bookings = mapBookingToBookingForClientDto(clientService.findReservations(clientId));
        if (!bookings.isEmpty()) {
            return ResponseEntity.ok(bookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
