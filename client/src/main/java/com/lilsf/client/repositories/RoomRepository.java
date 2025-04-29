package com.lilsf.client.repositories;



import com.lilsf.client.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findRoomByHotel_id(Integer id);

    Optional<Room> findRoomByHotel_IdAndId(int hotelId, int id);

    @Query("SELECT DISTINCT r FROM Room r" +
            " LEFT JOIN r.bookingList b WITH ((:curStartDate BETWEEN b.startDate AND b.endDate) OR (:curEndDate BETWEEN b.startDate AND b.endDate)) AND b.bookingStatus = 'CONFIRMED' " +
            " LEFT JOIN r.unavailabilityList u WITH (:curStartDate  BETWEEN u.startDate AND u.endDate) OR (:curEndDate  BETWEEN u.startDate AND u.endDate)" +
            " WHERE b.id IS NULL AND u.id IS NULL AND r.hotel.id = :hotelId "
    )
    List<Room> findAvailableRoom(@Param("curStartDate") Date startDate, @Param("curEndDate") Date endDate, @Param("hotelId") Integer hotelId);
}
