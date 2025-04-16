package com.lil_sf.booking.service;

import com.lil_sf.booking.models.Hotel;
import com.lil_sf.booking.models.Room;
import com.lil_sf.booking.repositories.HotelRepository;
import com.lil_sf.booking.repositories.RoomRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelSearchService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelSearchService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> findAvailableHotels(Date startDate, Date endDate) {
        return hotelRepository.findAvailableHotel(startDate, endDate);
    }

    public List<Room> getInformationHotels(int hotelId, Date startDate, Date endDate) {
        return roomRepository.findAvailableRoom(startDate, endDate, hotelId);
    }
}
