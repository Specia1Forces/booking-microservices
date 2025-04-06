package com.lil_sf.booking.repositories;

import com.lil_sf.booking.models.Booking;
import com.lil_sf.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Integer> {
    /*
    // Бронирования клиента
    List<Booking> findBookingByClient_ClientId(Integer clientId);

    Optional<Booking> findRoomByClient_ClientIdAndBookingId(Integer clientId, Integer bookingId);

     */

    Optional<Booking> findBookingByClient_Id(Integer clientId);

    @Query("SELECT b FROM Hotel h" +
            " JOIN h.roomList r" +
            " JOIN r.bookingList b  " +
            " WHERE h.hotelManager.id = :hotelManagerId "
    )
    List<Booking> findAvailableHotel(@Param("hotelManagerId") Integer hotelManagerId );
}
