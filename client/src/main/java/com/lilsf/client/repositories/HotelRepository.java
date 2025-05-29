package com.lilsf.client.repositories;


import com.lilsf.client.models.Hotel;
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


    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.address a WHERE a.city = :city")
    List<Hotel> findAvailableHotel(@Param("city") String city);


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
