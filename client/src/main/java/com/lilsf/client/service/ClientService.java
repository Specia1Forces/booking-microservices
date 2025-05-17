package com.lilsf.client.service;


import com.lilsf.client.models.Booking;
import com.lilsf.client.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ClientService {

    private final BookingRepository bookingRepository;

    public ClientService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findReservations(int clientId) {
        log.info("Finding reservations for client with ID: {}", clientId);
        return bookingRepository.findBookingByClient_Id(clientId);
    }

}
