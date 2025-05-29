package com.lilsf.client.controller;


import com.lilsf.client.dto.HotelForClientDto;
import com.lilsf.client.dto.RoomForClientDto;
import com.lilsf.client.dto.SearchHotelDto;
import com.lilsf.client.dto.SearchRoomDto;
import com.lilsf.client.models.Hotel;
import com.lilsf.client.service.HotelSearchService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HotelSearchController {
    HotelSearchService hotelSearchService;

    @Autowired
    public HotelSearchController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @PostMapping("/hotels/available")
    public ResponseEntity<List<HotelForClientDto>> findAvailableHotels(@RequestBody SearchHotelDto searchHotelDto) {
        log.info("Received request to find available hotels with search criteria: {}", searchHotelDto);
        List<HotelForClientDto> availableHotels = mapHotelToHotelForClientsDto(hotelSearchService.findAvailableHotels(searchHotelDto.getStartDate(), searchHotelDto.getEndDate(),searchHotelDto.getCity()));
        log.info("Found {} available hotels", availableHotels.size());
        return new ResponseEntity<>(availableHotels, HttpStatus.OK);
    }

    @PostMapping("/hotels/{hotelId}/information")
    public ResponseEntity<List<RoomForClientDto>> getInformationHotels(@PathVariable int hotelId, @RequestBody SearchRoomDto searchRoomDto) {
        log.info("Received request to get information for hotel with ID: {} with search criteria: {}", hotelId, searchRoomDto);
        List<RoomForClientDto> hotelInformation = mapRoomToRoomForClientDto(hotelSearchService.getInformationHotels(hotelId, searchRoomDto.getStartDate(), searchRoomDto.getEndDate()));
        log.info("Found {} rooms with information for hotel with ID: {}", hotelInformation.size(), hotelId);
        return new ResponseEntity<>(hotelInformation, HttpStatus.OK);
    }
}
