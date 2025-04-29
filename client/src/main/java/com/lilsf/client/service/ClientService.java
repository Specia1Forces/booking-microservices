package com.lilsf.client.service;


import com.lilsf.client.models.Booking;
import com.lilsf.client.repositories.BookingRepository;
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
