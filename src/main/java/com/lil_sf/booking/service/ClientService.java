package com.lil_sf.booking.service;

import com.lil_sf.booking.models.Booking;
import com.lil_sf.booking.repositories.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final BookingRepository bookingRepository;

    public ClientService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findReservations(int ClientId) {
        return bookingRepository.findBookingByClient_Id(ClientId);
    }

}
