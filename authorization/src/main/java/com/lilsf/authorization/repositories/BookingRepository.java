package com.lilsf.authorization.repositories;


import com.lilsf.authorization.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Integer> {
    /*
    // Бронирования клиента
    List<Booking> findBookingByClient_ClientId(Integer clientId);

    Optional<Booking> findRoomByClient_ClientIdAndBookingId(Integer clientId, Integer bookingId);

     */

    List<Booking>  findBookingByClient_Id(Integer clientId);

    @Query("SELECT b FROM Hotel h" +
            " JOIN h.roomList r" +
            " JOIN r.bookingList b  " +
            " WHERE h.hotelManager.id = :hotelManagerId "
    )
    List<Booking> findAvailableHotel(@Param("hotelManagerId") Integer hotelManagerId );
}
