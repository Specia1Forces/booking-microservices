package com.lil_sf.booking.controller;

import com.lil_sf.booking.models.Address;
import com.lil_sf.booking.models.Hotel;
import com.lil_sf.booking.models.Room;
import com.lil_sf.booking.service.HotelsService;
import com.lil_sf.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;@Controller
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
    public List<Hotel> listHotels() {
        int hotelManagerId = 0;
        return hotelsService.findAll(hotelManagerId);
    }

    @GetMapping("/hotels/{id}")//исправить mapping
    public Hotel getHotelById(@PathVariable int id) {
        int hotelManagerId = 0;
        return hotelsService.findOne(hotelManagerId, id);
    }

    @DeleteMapping("/hotels/{id}")
    public void deleteHotel(@PathVariable int id) {
        int hotelManagerId = 0;
        hotelsService.deleteById(hotelManagerId, id);
    }

    @PostMapping("/hotels/add")
    public Hotel addHotel(@RequestBody Hotel hotel, @RequestBody Address address) {
        int hotelManagerId = 0;
        return hotelsService.save(hotelManagerId, hotel, address);
    }

    @PutMapping("/hotels/{id}")
    public Hotel editHotel(@PathVariable int id, @RequestBody Hotel updatedHotel, @RequestBody Address updatedAddress) { //может ничего не надо возвращать
        int hotelManagerId = 0;
        return hotelsService.update(hotelManagerId, id, updatedHotel, updatedAddress);
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public List<Room> listRooms(@PathVariable int hotelId) {
        int hotelManagerId = 0;
        return roomService.findAll(hotelManagerId, hotelId);
    }

    @GetMapping("/hotels/{hotelId}/rooms/{roomId}") // Исправлено: убрано "hotel" из пути
    public Room getRoomById(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0; // Если hotelManagerId нужно получать из другого источника, измените это
        return roomService.findOne(hotelManagerId, hotelId, roomId);
    }


    @DeleteMapping("/hotels/{hotelId}/rooms/{roomId}")
    public void deleteRoom(@PathVariable int hotelId, @PathVariable int roomId) {
        int hotelManagerId = 0;
        roomService.deleteRoom(hotelManagerId, hotelId, roomId);
    }

    @PostMapping("/hotels/{hotelId}/rooms/add")
    public Room addRoom(@PathVariable int hotelId, @RequestBody Room room) {
        int hotelManagerId = 0;
        return roomService.save(hotelManagerId, hotelId, room);
    }

    @PutMapping("/hotels/{hotelId}/rooms/{id}")
    public Room editRoom(@PathVariable int hotelId, @PathVariable int id, @RequestBody Room updateRoom) { //может ничего не надо возвращать
        int hotelManagerId = 0;
        return roomService.update(hotelManagerId, hotelId, id, updateRoom);
    }

    /*
    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    public List<Hotel> findAvailableHotels() {
        int hotelManagerId = 0;
        return hotelSearchService.findAvailableHotels(hotelManagerId);
    }

     */
}




  /*
    private final HotelsService hotelsService;

    @Autowired
    public HotelManagerController(HotelsService hotelsService) {
        this.hotelsService = hotelsService;
    }

     */
    /*
    @GetMapping("/dashboard")
    public String dashboard() {
        return "hotelmanager/dashboard";
    }

    @GetMapping("/hotels")
    public String findHotel(Model model) {
        List<Hotel> hotelList = hotelsService.findAll();
        model.addAttribute("hotelList", hotelList);
        return "hotelmanager/hotel-list";
    }

    @GetMapping("/hotels/add")
    public String addHotelForm(@ModelAttribute("hotel") Hotel hotel) {
        return "hotelmanager/hotel-add";
    }

    @PostMapping("/hotels/add")
    public String addHotel(@ModelAttribute("hotel") Hotel hotel
    ) {
        hotelsService.save(hotel);
        return "redirect:/manager/hotels";
    }

    /*
    @GetMapping("/hotels/edit/{id}")
    public String hotelEdit(Model model, @PathVariable("id") int id){
        model.addAttribute("allServices",hotelsService.findOne(id));
        return "service_edit";
    }

     */

    /*
    @GetMapping("/service_edit/{id}")
    public String editService(Model model, @PathVariable("id") int id) {
        model.addAttribute("allServices", allServicesService.findOne(id));
        return "service_edit";
    }

    @PatchMapping("/all_services/{id}")
    public String updateServiceForm(@ModelAttribute("allServices") AllServices allServices,
                                    @PathVariable("id") int id) {


        allServicesService.update(id, allServices);
        return "redirect:/all_services";
    }

     */
