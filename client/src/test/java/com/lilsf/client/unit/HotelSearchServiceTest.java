package com.lilsf.client.unit;

import com.lilsf.client.models.Hotel;
import com.lilsf.client.models.Room;
import com.lilsf.client.models.enums.CategoryType;
import com.lilsf.client.models.enums.RoomType;
import com.lilsf.client.repositories.BookingRepository;
import com.lilsf.client.repositories.HotelRepository;
import com.lilsf.client.repositories.RoomRepository;
import com.lilsf.client.service.ClientService;
import com.lilsf.client.service.HotelSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {HotelSearchService.class

})
public class HotelSearchServiceTest {

    @Autowired
    private HotelSearchService hotelSearchService;
    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private RoomRepository roomRepository;

    @Test
    void testFindAvailableHotels_shouldReturnAvailableHotels_whenHotelsExist() {

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // 1 день позже

        Hotel hotel1 = Hotel.builder()
                .id(1)
                .name("Hotel One")
                .description("Description for Hotel One")
                .isActive(true)
                .categoryType(CategoryType.HOTEL)
                .build();

        Hotel hotel2 = Hotel.builder()
                .id(2)
                .name("Hotel Two")
                .description("Description for Hotel Two")
                .isActive(true)
                .categoryType(CategoryType.HOTEL)
                .build();

        List<Hotel> expectedHotels = new ArrayList<>();
        expectedHotels.add(hotel1);
        expectedHotels.add(hotel2);

        when(hotelRepository.findAvailableHotel(startDate, endDate)).thenReturn(expectedHotels);


        List<Hotel> actualHotels = hotelSearchService.findAvailableHotels(startDate, endDate);


        assertNotNull(actualHotels);
        assertEquals(2, actualHotels.size());
        assertEquals(expectedHotels, actualHotels);


    }

    @Test
    void testFindAvailableHotels_shouldReturnEmptyList_whenNoAvailableHotels() {

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // 1 день позже
        when(hotelRepository.findAvailableHotel(startDate, endDate)).thenReturn(new ArrayList<>());


        List<Hotel> actualHotels = hotelSearchService.findAvailableHotels(startDate, endDate);


        assertNotNull(actualHotels);
        assertTrue(actualHotels.isEmpty());


    }

    @Test
    void testGetInformationHotels_shouldReturnRooms_whenRoomsExistForHotel() {
        // Arrange
        int hotelId = 1;
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // 1 день позже

        Room room1 = Room.builder()
                .id(1)
                .name("Room One")
                .description("Description for Room One")
                .currentPrice(BigDecimal.valueOf(100))
                .roomType(RoomType.SINGLE)
                .hotel(null)
                .build();

        Room room2 = Room.builder()
                .id(2)
                .name("Room Two")
                .description("Description for Room Two")
                .currentPrice(BigDecimal.valueOf(150))
                .roomType(RoomType.DOUBLE)
                .hotel(null)
                .build();

        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(room1);
        expectedRooms.add(room2);

        when(roomRepository.findAvailableRoom(startDate, endDate, hotelId)).thenReturn(expectedRooms);


        List<Room> actualRooms = hotelSearchService.getInformationHotels(hotelId, startDate, endDate);


        assertNotNull(actualRooms);
        assertEquals(2, actualRooms.size());
        assertEquals(expectedRooms, actualRooms);

    }

    @Test
    void testGetInformationHotels_shouldReturnEmptyList_whenNoRoomsAvailable() {

        int hotelId = 1;
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); //
        when(roomRepository.findAvailableRoom(startDate, endDate, hotelId)).thenReturn(new ArrayList<>());


        List<Room> actualRooms = hotelSearchService.getInformationHotels(hotelId, startDate, endDate);


        assertNotNull(actualRooms);
        assertTrue(actualRooms.isEmpty());

    }
}
