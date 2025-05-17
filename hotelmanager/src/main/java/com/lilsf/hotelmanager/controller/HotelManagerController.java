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
        return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelsService.findAll(hotelManagerId)), HttpStatus.OK);
    }

    @GetMapping("/hotels/{id}")//исправить mapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Hotel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    public ResponseEntity<HotelForManagerDto> getHotelById(@PathVariable int id) {
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelsService.findOne(hotelManagerId, id)), HttpStatus.OK);
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable int id) {
        int hotelManagerId = 0;
        hotelsService.deleteById(hotelManagerId, id);
        return new ResponseEntity<>("Hotel is deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/hotels/add")
    public ResponseEntity<HotelForManagerDto> addHotel(@RequestBody HotelDto hotel) {
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelsService.save(hotelManagerId, mapHotelForManagerDtoToHotel(hotel), mapHotelForManagerDtoToAddress(hotel))), HttpStatus.OK);
    }

    @PutMapping("/hotels/{id}")
    /*
    @Operation(summary = "Edit an existing tit hotel", description = "Updates an existing hotel with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the hotel",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HotelForManagerDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "text/plain", // Указываем text/plain для String
                            schema = @Schema(type = "string", example = "The manager's ID does not match when add a hotel")))
    })

     */
    public ResponseEntity<HotelForManagerDto> editHotel(@PathVariable int id, @RequestBody HotelDto updatedHotel) { //может ничего не надо возвращать
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelsService.update(hotelManagerId, id, mapHotelForManagerDtoToHotel(updatedHotel), mapHotelForManagerDtoToAddress(updatedHotel))), HttpStatus.OK);
        /*
        try {
            return new ResponseEntity<>(mapHotelToHotelForClientsDto(hotelsService.update(hotelManagerId, id, mapHotelForManagerDtoToHotel(updatedHotel), mapHotelForManagerDtoToAddress(updatedHotel))), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("The manager's ID does not match when add a hotel", HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomForManagerDto>> listRooms(@PathVariable int hotelId) {
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.findAll(hotelManagerId, hotelId)), HttpStatus.OK);
        /*
        try {
            return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.findAll(hotelManagerId, hotelId)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("The manager's ID does not match when find a rooms", HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */
    }

    @GetMapping("/hotels/{hotelId}/rooms/{roomId}") // Исправлено: убрано "hotel" из пути
    public ResponseEntity<RoomForManagerDto> getRoomById(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.findOne(hotelManagerId, hotelId, roomId)), HttpStatus.OK);
    }


    @DeleteMapping("/hotels/{hotelId}/rooms/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0;
        roomService.deleteRoom(hotelManagerId, hotelId, roomId);
        return new ResponseEntity<>("Room is deleted successfully!", HttpStatus.OK);
        /*
        try {
            roomService.deleteRoom(hotelManagerId, hotelId, roomId);
            return new ResponseEntity<>("Room is deleted successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("The manager's ID does not match when delete a room", HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */
    }

    @PostMapping("/hotels/{hotelId}/rooms/add")
    public ResponseEntity<RoomForManagerDto> addRoom(@PathVariable int hotelId, @RequestBody RoomDto room) {
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.save(hotelManagerId, hotelId, mapRoomForManagerDtoToRoom(room))), HttpStatus.OK);
        /*
        try {
            return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.save(hotelManagerId, hotelId, mapRoomForManagerDtoToRoom(room))), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("The manager's ID does not match when adding a room", HttpStatus.INTERNAL_SERVER_ERROR);
        }

         */
    }

    @PutMapping("/hotels/{hotelId}/rooms/{id}")
    public ResponseEntity<RoomForManagerDto> editRoom(@PathVariable int hotelId, @PathVariable int id, @RequestBody RoomDto updateRoom) { //может ничего не надо возвращать
        int hotelManagerId = 0;
        return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.update(hotelManagerId, hotelId, id, mapRoomForManagerDtoToRoom(updateRoom))), HttpStatus.OK);
        /*
        try {
            return new ResponseEntity<>(mapRoomToRoomForManagerDto(roomService.update(hotelManagerId, hotelId, id, mapRoomForManagerDtoToRoom(updateRoom))), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("The manager's ID does not match when edit a room", HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */
    }

    /*
    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    public List<Hotel> findAvailableHotels() {
        int hotelManagerId = 0;
        return hotelSearchService.findAvailableHotels(hotelManagerId);
    }

     */
}


