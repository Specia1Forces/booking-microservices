package com.lil_sf.booking.controller;

import com.lil_sf.booking.models.Hotel;
import com.lil_sf.booking.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class HotelManagerController {

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
}
