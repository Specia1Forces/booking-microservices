package com.lilsf.client.unit;

import com.lilsf.client.models.Booking;
import com.lilsf.client.models.enums.BookingStatus;
import com.lilsf.client.repositories.BookingRepository;
import com.lilsf.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {ClientService.class

})

public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @MockBean
    BookingRepository bookingRepository;

    @Test
    void testFindReservations_shouldReturnReservations_whenHReservationsHave() {
        int clientId = 1;

        Booking booking1 = Booking.builder()
                .id(1)
                .startDate(new Date())
                .endDate(new Date())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .discountPercent(BigDecimal.ZERO)
                .bookingStatus(BookingStatus.CONFIRMED)
                .totalPrice(BigDecimal.valueOf(100))
                .client(null)
                .room(null)
                .build();

        Booking booking2 = Booking.builder()
                .id(2)
                .startDate(new Date())
                .endDate(new Date())
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .discountPercent(BigDecimal.ZERO)
                .bookingStatus(BookingStatus.CONFIRMED)
                .totalPrice(BigDecimal.valueOf(200))
                .client(null)
                .room(null)
                .build();

        List<Booking> expectedBookings = new ArrayList<>();
        expectedBookings.add(booking1);
        expectedBookings.add(booking2);

        when(bookingRepository.findBookingByClient_Id(clientId)).thenReturn(expectedBookings);


        List<Booking> actualBookings = clientService.findReservations(clientId);


        assertNotNull(actualBookings);
        assertEquals(2, actualBookings.size());
        assertEquals(expectedBookings, actualBookings);


    }

    @Test
    void testFindReservations_shouldReturnEmptyList_whenNoReservations() {

        int clientId = 2;
        when(bookingRepository.findBookingByClient_Id(clientId)).thenReturn(new ArrayList<>());


        List<Booking> actualBookings = clientService.findReservations(clientId);


        assertNotNull(actualBookings);
        assertTrue(actualBookings.isEmpty());

    }
}

