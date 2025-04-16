package com.lil_sf.booking.service;

import com.lil_sf.booking.models.Booking;
import com.lil_sf.booking.repositories.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookingService {
}
