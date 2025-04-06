package com.lil_sf.booking.service;

import com.lil_sf.booking.models.Booking;
import com.lil_sf.booking.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReservationsOfClientsService {

    @Autowired
    private final BookingRepository bookingRepository;

    public ReservationsOfClientsService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

/*
    public Booking findOne(int id){
        Integer clientID = 0;// id клиента
        Optional<Booking> foundPerson = bookingRepository.findRoomByClient_ClientIdAndBookingId(clientID,id);
        return foundPerson.orElse(null);
    }

    public List<Booking> findAll(){
        Integer clientID = 0;// id клиента
        return bookingRepository.findBookingByClient_ClientId(clientID);
    }

    @Transactional
    public void save(Booking booking) {
        //дописать нужные сеттеры (id владельца)
        bookingRepository.save(booking);
    }


    @Transactional
    public void delete(int id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Booking updatedBooking) {
        updatedBooking.setBookingId(id);
        bookingRepository.save(updatedBooking);
    }

 */
}
