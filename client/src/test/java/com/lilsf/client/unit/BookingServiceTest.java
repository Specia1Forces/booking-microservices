package com.lilsf.client.unit;

import com.lilsf.client.models.Booking;
import com.lilsf.client.models.Client;
import com.lilsf.client.models.Room;
import com.lilsf.client.models.enums.BookingStatus;
import com.lilsf.client.repositories.BookingRepository;
import com.lilsf.client.repositories.ClientRepository;
import com.lilsf.client.repositories.RoomRepository;
import com.lilsf.client.service.BookingService;
import com.lilsf.client.service.ClientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookingService.class

})
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private ClientRepository clientRepository;


    @Test
    void makeReservations_successfulBooking() {
        int clientId = 1;
        int roomId = 10;
        Date startDate = Date.from(LocalDate.of(2024, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDate.of(2024, 6, 3).atStartOfDay(ZoneId.systemDefault()).toInstant());

        Client client = Client.builder().id(clientId).build();
        Room room = Room.builder().id(roomId).currentPrice(BigDecimal.valueOf(100)).build();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.findAvailableRoom(startDate, endDate, clientId)).thenReturn(Optional.empty());

        Booking expectedBooking = Booking.builder()
                .startDate(startDate)
                .endDate(endDate)
                .discountPercent(BigDecimal.ZERO)
                .totalPrice(BigDecimal.valueOf(200))
                .bookingStatus(BookingStatus.CONFIRMED)
                .client(client)
                .room(room)
                .build();
        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);
        assertDoesNotThrow(() -> bookingService.makeReservations(clientId, roomId, startDate, endDate));
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
    @Test
    void makeReservations_clientOrRoomNotFound_throwsException() {
        int clientId = 2;
        int roomId = 5;
        Date startDate = new Date();
        Date endDate = new Date();
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookingService.makeReservations(clientId, roomId, startDate, endDate)
        );
        assertEquals("The client or hotel room does not exist", exception.getMessage());
        verify(bookingRepository, never()).save(any());
    }
    @Test
    void makeReservations_roomAlreadyBooked_throwsException() {
        int clientId = 3;
        int roomId = 15;
        Date startDate = new Date();
        Date endDate = new Date();
        Client client = Client.builder().id(clientId).build();
        Room room = Room.builder().id(roomId).currentPrice(BigDecimal.valueOf(150)).build();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.findAvailableRoom(startDate, endDate, clientId)).thenReturn(Optional.of(room));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookingService.makeReservations(clientId, roomId, startDate, endDate)
        );
        assertEquals("The room is already booked", exception.getMessage());
        verify(bookingRepository, never()).save(any());
    }
}
