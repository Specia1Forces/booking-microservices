package com.lilsf.client.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilsf.client.controller.BookingController;
import com.lilsf.client.controller.HotelSearchController;
import com.lilsf.client.dto.*;
import com.lilsf.client.models.Address;
import com.lilsf.client.models.Hotel;
import com.lilsf.client.models.Room;
import com.lilsf.client.models.enums.RoomType;
import com.lilsf.client.service.BookingService;
import com.lilsf.client.service.HotelSearchService;
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

@WebMvcTest(HotelSearchController.class)
public class HotelSearchControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HotelSearchService hotelSearchService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAvailableHotels_returnsHotelsList() throws Exception {
        SearchHotelDto searchHotelDto = SearchHotelDto.builder()
                .city("Paris")
                .startDate(new Date())
                .endDate(new Date())
                .build();
        List<Hotel> foundHotels = List.of(
                Hotel.builder()
                        .id(1)
                        .name("Paris Resort")
                        .address(Address.builder().city("Paris").build())
                        .build()
        );
        when(hotelSearchService.findAvailableHotels(any(Date.class), any(Date.class), eq("Paris")))
                .thenReturn(foundHotels);
        mockMvc.perform(get("/api/search/hotels/available")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchHotelDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Paris Resort"))
                .andExpect(jsonPath("$[0].city").value("Paris"));
    }

    @Test
    void findAvailableHotels_returnsEmptyList() throws Exception {
        SearchHotelDto searchHotelDto = SearchHotelDto.builder()
                .city("Mars")
                .startDate(new Date())
                .endDate(new Date())
                .build();
        when(hotelSearchService.findAvailableHotels(any(Date.class), any(Date.class), eq("Mars")))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/search/hotels/available")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchHotelDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getInformationHotels_returnsRoomsList() throws Exception {
        int hotelId = 5;
        SearchRoomDto searchRoomDto = SearchRoomDto.builder()
                .startDate(new Date())
                .endDate(new Date())
                .build();
        List<Room> foundRooms = List.of(
                Room.builder()
                        .id(1)
                        .name("Suite")
                        .currentPrice(BigDecimal.valueOf(250))
                        .roomType(RoomType.SINGLE)
                        .build()
        );
        when(hotelSearchService.getInformationHotels(eq(hotelId), any(Date.class), any(Date.class)))
                .thenReturn(foundRooms);
        mockMvc.perform(get("/api/search/hotels/{hotelId}/information", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRoomDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Suite"))
                .andExpect(jsonPath("$[0].currentPrice").value(250));
    }

    @Test
    void getInformationHotels_returnsEmptyList() throws Exception {
        int hotelId = 99;
        SearchRoomDto searchRoomDto = SearchRoomDto.builder()
                .startDate(new Date())
                .endDate(new Date())
                .build();
        when(hotelSearchService.getInformationHotels(eq(hotelId), any(Date.class), any(Date.class)))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/search/hotels/{hotelId}/information", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRoomDto)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


}
