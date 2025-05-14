package com.lilsf.hotelmanager.integration;

import com.lilsf.hotelmanager.dto.HotelDto;
import com.lilsf.hotelmanager.dto.HotelForManagerDto;
import com.lilsf.hotelmanager.dto.RoomDto;
import com.lilsf.hotelmanager.dto.RoomForManagerDto;
import com.lilsf.hotelmanager.models.Address;
import com.lilsf.hotelmanager.models.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilsf.hotelmanager.controller.HotelManagerController;
import com.lilsf.hotelmanager.models.HotelManager;
import com.lilsf.hotelmanager.models.Room;
import com.lilsf.hotelmanager.models.enums.CategoryType;
import com.lilsf.hotelmanager.service.HotelsService;
import com.lilsf.hotelmanager.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.lilsf.hotelmanager.controller.mapper.HotelAndAddressForManagerMapper.mapHotelForManagerDtoToAddress;
import static com.lilsf.hotelmanager.controller.mapper.HotelAndAddressForManagerMapper.mapHotelForManagerDtoToHotel;
import static com.lilsf.hotelmanager.controller.mapper.HotelForManagerMapper.mapHotelToHotelForClientsDto;
import static com.lilsf.hotelmanager.controller.mapper.RoomForManagerMapper.mapRoomForManagerDtoToRoom;
import static com.lilsf.hotelmanager.controller.mapper.RoomForManagerMapper.mapRoomToRoomForManagerDto;

@WebMvcTest(HotelManagerController.class)
public class HotelManagerControllerTest {


    @MockBean
    private HotelsService hotelsService;
    @MockBean
    private RoomService roomService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listHotels() throws Exception {
        List<Hotel> hotels = List.of(
                Hotel.builder()
                        .id(1)
                        .name("Hotel 1")
                        .description("Description 1")
                        .isActive(true)
                        .categoryType(CategoryType.HOTEL)
                        .address(new Address())
                        .hotelManager(new HotelManager())
                        .build()
        );

        when(hotelsService.findAll(anyInt())).thenReturn(hotels);
        //Mockito.when(hotelsService.findAll(anyInt())).thenReturn(hotels);

        mockMvc.perform(get("/api/manager/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Hotel 1"));
    }

    @Test
    void getHotelById() throws Exception {
        Hotel hotel = Hotel.builder()
                .id(1)
                .name("Hotel 1")
                .description("Description 1")
                .isActive(true)
                .categoryType(CategoryType.HOTEL)
                .address(new Address())
                .hotelManager(new HotelManager())
                .build();

        when(hotelsService.findOne(anyInt(), anyInt())).thenReturn(hotel);

        mockMvc.perform(get("/api/manager/hotels/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Hotel 1"));
    }

    @Test
    void deleteHotel() throws Exception {
        doNothing().when(hotelsService).deleteById(anyInt(), anyInt());

        mockMvc.perform(delete("/api/manager/hotels/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Hotel is deleted successfully!"));
    }

    @Test
    void addHotel() throws Exception {
        Hotel newHotel = Hotel.builder()
                .name("New Hotel")
                .description("New Description")
                .isActive(true)
                .categoryType(CategoryType.HOTEL)
                .address(new Address())
                .hotelManager(new HotelManager())
                .build();

        Hotel createdHotel = Hotel.builder()
                .id(2)
                .name("New Hotel")
                .description("New Description")
                .isActive(true)
                .categoryType(CategoryType.HOTEL)
                .address(new Address())
                .hotelManager(new HotelManager())
                .build();

        when(hotelsService.save(anyInt(), any(Hotel.class), any(Address.class))).thenReturn(createdHotel);

        mockMvc.perform(post("/api/manager/hotels/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHotel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Hotel"));
    }


}
