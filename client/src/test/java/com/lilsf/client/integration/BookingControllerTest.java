package com.lilsf.client.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilsf.client.controller.BookingController;
import com.lilsf.client.dto.reservationHotelDto;
import com.lilsf.client.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void reservationHotels_success() throws Exception {

        reservationHotelDto reservationDto = reservationHotelDto.builder()
                .roomId(42)
                .startDate(new Date())
                .endDate(new Date())
                .build();

        doNothing().when(bookingService)
                .makeReservations(eq(1), eq(42), any(Date.class), any(Date.class));

        mockMvc.perform(post("/api/booking/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Room is reservation successfully!"));
    }


}