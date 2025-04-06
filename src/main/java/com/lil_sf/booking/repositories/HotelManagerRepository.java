package com.lil_sf.booking.repositories;


import com.lil_sf.booking.models.HotelManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelManagerRepository extends JpaRepository<HotelManager, Integer> {
    //Optional<Room> findRoomByHotel_HotelIdAndRoomId(Integer hotelHotelId, Integer roomId);

    //Optional<HotelManager> findHotelManagerByHotelManagerId_HotelManagerId(Integer hotelManagerId);

    Optional<HotelManager> findHotelManagerByUser_Id(Integer userId);

    //Optional<HotelManager> findHotelManagerByUser_Id(Integer userId);
}
