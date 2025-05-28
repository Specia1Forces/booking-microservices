package com.lilsf.client.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilsf.client.controller.BookingController;
import com.lilsf.client.controller.ClientController;
import com.lilsf.client.dto.BookingForClientDto;
import com.lilsf.client.dto.reservationHotelDto;
import com.lilsf.client.models.Booking;
import com.lilsf.client.models.Client;
import com.lilsf.client.models.Hotel;
import com.lilsf.client.models.Room;
import com.lilsf.client.service.BookingService;
import com.lilsf.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;


    @Test
    void findReservations_returnsReservationList() throws Exception {
        List<Booking> bookings = List.of(
                Booking.builder()
                        .id(1)
                        .startDate(new Date())
                        .endDate(new Date())
                        .client(new Client())
                        .room(Room.builder().hotel(new Hotel()).build())
                        .build()
        );
        when(clientService.findReservations(eq(4))).thenReturn(bookings);
        mockMvc.perform(get("/api/client/{clientId}/reservations", 4))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void findReservations_returnsNotFound_whenNoReservations() throws Exception {
        when(clientService.findReservations(eq(99))).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/client/{clientId}/reservations", 99))
                .andExpect(status().isNotFound());
    }

}