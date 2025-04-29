package com.lilsf.client.controller;


import com.lilsf.client.dto.HotelForClientDto;
import com.lilsf.client.dto.RoomForClientDto;
import com.lilsf.client.dto.SearchHotelDto;
import com.lilsf.client.dto.SearchRoomDto;
import com.lilsf.client.models.Hotel;
import com.lilsf.client.service.HotelSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.lilsf.client.controller.mapper.HotelForClientMapper.mapHotelToHotelForClientsDto;
import static com.lilsf.client.controller.mapper.RoomForClientMapper.mapRoomToRoomForClientDto;

@RestController
@RequestMapping("/api/search")
public class HotelSearchController {
    HotelSearchService hotelSearchService;

    @Autowired
    public HotelSearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @GetMapping("/hotels/available")
    public ResponseEntity<List<HotelForClientDto>> findAvailableHotels(@RequestBody SearchHotelDto searchHotelDto) { // дописать
        // дописать город
        return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelSearchService.findAvailableHotels(searchHotelDto.getStartDate(), searchHotelDto.getEndDate())), HttpStatus.OK);
    }

    @GetMapping("/hotels/{hotelId}/information")
    public ResponseEntity<List<RoomForClientDto>> getInformationHotels(@PathVariable int hotelId, @RequestBody SearchRoomDto searchRoomDto) {
        return new ResponseEntity<>(mapRoomToRoomForClientDto(hotelSearchService.getInformationHotels(hotelId, searchRoomDto.getStartDate(), searchRoomDto.getEndDate())), HttpStatus.OK);
    }
}
