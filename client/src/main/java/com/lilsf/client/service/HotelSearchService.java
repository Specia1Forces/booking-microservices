package com.lilsf.client.service;


import com.lilsf.client.models.Hotel;
import com.lilsf.client.models.Room;
import com.lilsf.client.repositories.HotelRepository;
import com.lilsf.client.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class HotelSearchService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelSearchService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> findAvailableHotels(Date startDate, Date endDate, String city) {
        // дописать по городу
        log.info("Finding available hotels between {} and {}", startDate, endDate);
        return hotelRepository.findAvailableHotel(city);
    }

    public List<Room> getInformationHotels(int hotelId, Date startDate, Date endDate) {
        log.info("Getting information for rooms in hotel with ID: {} between {} and {}", hotelId, startDate, endDate);
        return roomRepository.findAvailableRooms(startDate, endDate, hotelId);
    }
}
