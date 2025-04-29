package com.lilsf.authorization.repositories;


import com.lilsf.authorization.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    //List<Hotel> findHotelByHotelManager_HotelManagerId(Integer hotelManagerId);
    List<Hotel> findHotelByHotelManager_Id(Integer id);

    // может ошибка из-за ID
    Optional<Hotel> findHotelByIdAndHotelManager_Id(Integer id, Integer managerId);

    void deleteHotelByIdAndHotelManager_Id(Integer id, Integer managerId);


    @Query("SELECT DISTINCT h FROM Hotel h" +
            " JOIN h.address a" +
            " JOIN h.roomList r" +
            " LEFT JOIN r.bookingList b WITH ((:curStartDate BETWEEN b.startDate AND b.endDate) OR (:curEndDate BETWEEN b.startDate AND b.endDate)) AND b.bookingStatus = 'CONFIRMED' " +
            " LEFT JOIN r.unavailabilityList u WITH (:curStartDate  BETWEEN u.startDate AND u.endDate) OR (:curEndDate  BETWEEN u.startDate AND u.endDate)" +
            " WHERE b.id IS NULL AND u.id IS NULL"
            )
    List<Hotel> findAvailableHotel(@Param("curStartDate") Date startDate,@Param("curEndDate") Date endDate );

    /*
    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
User findUserByStatusAndNameNamedParams(
  @Param("status") Integer status,
  @Param("name") String name);
     */


    /*
    void deleteHotelByHotelIdAndHotelManager_HotelId(Integer hotelId, Integer hotelManagerId);
    @Query("SELECT h FROM Hotel h inner join h.address addr, " +
            "Room r inner join h," +
            "Unavailability u inner join r," +
            "Booking b inner join r")
    List<Hotel> findHotel();

     */

}
