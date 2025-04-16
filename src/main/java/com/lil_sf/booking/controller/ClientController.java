package com.lil_sf.booking.controller;

import com.lil_sf.booking.models.Booking;
import com.lil_sf.booking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/client")
public class ClientController {
    public final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{clientId}/reservations")
    public List<Booking> findReservations(@PathVariable int clientId) {
        return clientService.findReservations(clientId);
    }
}
