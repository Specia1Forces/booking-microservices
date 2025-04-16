package com.lil_sf.booking.controller;

import com.lil_sf.booking.models.Hotel;
import com.lil_sf.booking.models.Room;
import com.lil_sf.booking.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class HotelSearchController {
    HotelSearchService hotelSearchService;

    @Autowired
    public HotelSearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @GetMapping("/hotels/available")
    public List<Hotel> findAvailableHotels(int hotelId) {
        Date startDate = new Date();
        Date endDate = new Date();
        return hotelSearchService.findAvailableHotels(startDate, endDate);
    }

    @GetMapping("/hotels/{hotelId}/information")
    public List<Room> getInformationHotels(@PathVariable int hotelId) {
        Date startDate = new Date();
        Date endDate = new Date();
        return hotelSearchService.getInformationHotels(hotelId, startDate, endDate);
    }
}
