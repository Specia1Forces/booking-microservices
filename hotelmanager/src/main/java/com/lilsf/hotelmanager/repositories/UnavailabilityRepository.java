package com.lilsf.hotelmanager.repositories;


import com.lilsf.hotelmanager.models.Booking;
import com.lilsf.hotelmanager.models.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnavailabilityRepository extends JpaRepository<Unavailability, Integer> {

    @Query("SELECT un FROM Hotel h" +
            " JOIN h.roomList r" +
            " JOIN r.unavailabilityList un" +
            " WHERE h.hotelManager.id = :hotelManagerId "
    )
    List<Booking> findAvailableHotel(@Param("hotelManagerId") Integer hotelManagerId );

}
