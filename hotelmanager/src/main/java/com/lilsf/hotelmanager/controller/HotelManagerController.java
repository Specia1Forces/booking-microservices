package com.lilsf.hotelmanager.controller;


import com.lilsf.hotelmanager.dto.HotelDto;
import com.lilsf.hotelmanager.dto.HotelForManagerDto;
import com.lilsf.hotelmanager.dto.RoomDto;
import com.lilsf.hotelmanager.dto.RoomForManagerDto;
import com.lilsf.hotelmanager.models.Hotel;
import com.lilsf.hotelmanager.service.HotelsService;
import com.lilsf.hotelmanager.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.lilsf.hotelmanager.controller.mapper.HotelAndAddressForManagerMapper.mapHotelForManagerDtoToAddress;
import static com.lilsf.hotelmanager.controller.mapper.HotelAndAddressForManagerMapper.mapHotelForManagerDtoToHotel;
import static com.lilsf.hotelmanager.controller.mapper.HotelForManagerMapper.mapHotelToHotelForClientsDto;
import static com.lilsf.hotelmanager.controller.mapper.RoomForManagerMapper.mapRoomForManagerDtoToRoom;
import static com.lilsf.hotelmanager.controller.mapper.RoomForManagerMapper.mapRoomToRoomForManagerDto;

@Controller
@RequestMapping("/api/manager")
@Slf4j
public class HotelManagerController {

    private final HotelsService hotelsService;
    private final RoomService roomService;

    @Autowired
    public HotelManagerController(HotelsService hotelsService, RoomService roomService) {
        this.hotelsService = hotelsService;
        this.roomService = roomService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelForManagerDto>> listHotels() {
        int hotelManagerId = 0;
        log.info("Received request to list all hotels for manager with ID: {}", hotelManagerId);
        List<HotelForManagerDto> hotelsDto = mapHotelToHotelForClientsDto(hotelsService.findAll(hotelManagerId));
        log.info("Returning {} hotels", hotelsDto.size());
        return new ResponseEntity<>(hotelsDto, HttpStatus.OK);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelForManagerDto> getHotelById(@PathVariable int id) {
        int hotelManagerId = 0;
        log.info("Received request to get hotel with ID: {} for manager with ID: {}", id, hotelManagerId);
        HotelForManagerDto hotelDto = mapHotelToHotelForClientsDto(hotelsService.findOne(hotelManagerId, id));
        log.info("Returning hotel: {}", hotelDto);
        return new ResponseEntity<>(hotelDto, HttpStatus.OK);
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable int id) {
        int hotelManagerId = 0;
        log.info("Received request to delete hotel with ID: {} for manager with ID: {}", id, hotelManagerId);
        hotelsService.deleteById(hotelManagerId, id);
        log.info("Hotel with ID: {} deleted successfully", id);
        return new ResponseEntity<>("Hotel is deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/hotels/add")
    public ResponseEntity<HotelForManagerDto> addHotel(@RequestBody HotelDto hotel) {
        int hotelManagerId = 0;
        log.info("Received request to add hotel: {} for manager with ID: {}", hotel, hotelManagerId);
        HotelForManagerDto savedHotelDto = mapHotelToHotelForClientsDto(hotelsService.save(hotelManagerId, mapHotelForManagerDtoToHotel(hotel), mapHotelForManagerDtoToAddress(hotel)));
        log.info("Hotel added successfully with ID: {}", savedHotelDto.getId());
        return new ResponseEntity<>(savedHotelDto, HttpStatus.OK);
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<HotelForManagerDto> editHotel(@PathVariable int id, @RequestBody HotelDto updatedHotel) {
        int hotelManagerId = 0;
        log.info("Received request to update hotel with ID: {} with data: {} for manager with ID: {}", id, updatedHotel, hotelManagerId);
        HotelForManagerDto updatedHotelDtoResult = mapHotelToHotelForClientsDto(hotelsService.update(hotelManagerId, id, mapHotelForManagerDtoToHotel(updatedHotel), mapHotelForManagerDtoToAddress(updatedHotel)));
        log.info("Hotel updated successfully with ID: {}", updatedHotelDtoResult.getId());
        return new ResponseEntity<>(updatedHotelDtoResult, HttpStatus.OK);
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomForManagerDto>> listRooms(@PathVariable int hotelId) {
        int hotelManagerId = 0;
        log.info("Received request to list rooms for hotel with ID: {} for manager with ID: {}", hotelId, hotelManagerId);
        List<RoomForManagerDto> roomsDto = mapRoomToRoomForManagerDto(roomService.findAll(hotelManagerId, hotelId));
        log.info("Returning {} rooms for hotel with ID: {}", roomsDto.size(), hotelId);
        return new ResponseEntity<>(roomsDto, HttpStatus.OK);
    }

    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    public ResponseEntity<RoomForManagerDto> getRoomById(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0;
        log.info("Received request to get room with ID: {} for hotel with ID: {} for manager with ID: {}", roomId, hotelId, hotelManagerId);
        RoomForManagerDto roomDto = mapRoomToRoomForManagerDto(roomService.findOne(hotelManagerId, hotelId, roomId));
        log.info("Returning room: {}", roomDto);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }


    @DeleteMapping("/hotels/{hotelId}/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0;
        log.info("Received request to delete room with ID: {} for hotel with ID: {} for manager with ID: {}", roomId, hotelId, hotelManagerId);
        roomService.deleteRoom(hotelManagerId, hotelId, roomId);
        log.info("Room with ID: {} for hotel with ID: {} deleted successfully", roomId, hotelId);
        return new ResponseEntity<>("Room is deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/hotels/{hotelId}/rooms/add")
    public ResponseEntity<RoomForManagerDto> addRoom(@PathVariable int hotelId, @RequestBody RoomDto room) {
        int hotelManagerId = 0;
        log.info("Received request to add room: {} for hotel with ID: {} for manager with ID: {}", room, hotelId, hotelManagerId);
        RoomForManagerDto savedRoomDto = mapRoomToRoomForManagerDto(roomService.save(hotelManagerId, hotelId, mapRoomForManagerDtoToRoom(room)));
        log.info("Room added successfully with ID: {} for hotel with ID: {}", savedRoomDto.getRoomId(), hotelId);
        return new ResponseEntity<>(savedRoomDto, HttpStatus.OK);
    }

    @PutMapping("/hotels/{hotelId}/rooms/{id}")
    public ResponseEntity<RoomForManagerDto> editRoom(@PathVariable int hotelId, @PathVariable int id, @RequestBody RoomDto updateRoom) { //может ничего не надо возвращать
        int hotelManagerId = 0;
        log.info("Received request to update room with ID: {} for hotel with ID: {} with data: {} for manager with ID: {}", id, hotelId, updateRoom, hotelManagerId);
        RoomForManagerDto updatedRoomDtoResult = mapRoomToRoomForManagerDto(roomService.update(hotelManagerId, hotelId, id, mapRoomForManagerDtoToRoom(updateRoom)));
        log.info("Room with ID: {} for hotel with ID: {} updated successfully", id, hotelId);
        return new ResponseEntity<>(updatedRoomDtoResult, HttpStatus.OK);
    }

    /*
    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    public List<Hotel> findAvailableHotels() {
        int hotelManagerId = 0;
        return hotelSearchService.findAvailableHotels(hotelManagerId);
    }

     */
}


