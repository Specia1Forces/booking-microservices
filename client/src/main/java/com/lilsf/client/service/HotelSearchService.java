package com.lilsf.client.service;


import com.lilsf.client.models.Hotel;
import com.lilsf.client.models.Room;
import com.lilsf.client.repositories.HotelRepository;
import com.lilsf.client.repositories.RoomRepository;
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
