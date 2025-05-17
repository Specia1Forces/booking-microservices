package com.lilsf.hotelmanager.service;


import com.lilsf.hotelmanager.models.Address;
import com.lilsf.hotelmanager.models.Hotel;
import com.lilsf.hotelmanager.models.HotelManager;
import com.lilsf.hotelmanager.models.User;
import com.lilsf.hotelmanager.repositories.AddressRepository;
import com.lilsf.hotelmanager.repositories.HotelManagerRepository;
import com.lilsf.hotelmanager.repositories.HotelRepository;
import com.lilsf.hotelmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class HotelsService {
    private final HotelRepository hotelRepository;
    private final HotelManagerRepository hotelManagerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public HotelsService(HotelRepository hotelRepository, HotelManagerRepository hotelManagerRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelManagerRepository = hotelManagerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public Hotel findOne(int hotelManagerId, int id) {
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(id, hotelManagerId);
        return foundHotel.orElse(null);
    }

    public List<Hotel> findAll(int hotelManagerId) {
        return hotelRepository.findHotelByHotelManager_Id(hotelManagerId);
    }

    @Transactional
    public Hotel save(int hotelManagerId, Hotel hotel, Address address) {
        addressRepository.save(address);
        Optional<HotelManager> hotelManager = hotelManagerRepository.findHotelManagerByUser_Id(hotelManagerId);
        hotel.setHotelManager(hotelManager.get());
        hotel.setAddress(address);
        // сохранять еще адрес
        return  hotelRepository.save(hotel);
    }

    @Transactional
    public void deleteById(int hotelManagerId, int id) {
        hotelRepository.deleteHotelByIdAndHotelManager_Id(id, hotelManagerId);
    }

    @Transactional
    public Hotel update(int hotelManagerId, int id, Hotel updatedHotel, Address updatedAddress) {
        //  проверка на владельца
        if (updatedHotel.getHotelManager().getId() == hotelManagerId) {
            // нужно добавить проверку на обновления номера, может не было такого объета
            // нужно установить setId для updtate
            addressRepository.save(updatedAddress);
            updatedHotel.setId(id);
            updatedHotel.setAddress(updatedAddress);
            // обновить адресс
            return hotelRepository.save(updatedHotel);
        } else {
            throw new IllegalArgumentException("The manager's ID does not match when adding a hotel");
        }
    }


    public int getCurrentManagerId() {
        Optional<User> foundUsers = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return foundUsers(foundUsers);
    }


    private int foundUsers(Optional<User> foundUsers) {
        if (foundUsers.isEmpty()) {
            throw new RuntimeException("The user was not found");
        }
        //hotelManagerRepository.findById()
        Optional<HotelManager> foundHotelManager = hotelManagerRepository.findHotelManagerByUser_Id(foundUsers.get().getId());

        if (foundHotelManager.isEmpty()) {
            throw new RuntimeException("The hotel manager was not found");
        }

        return foundHotelManager.get().getId();
    }


    public int getCurrentManagerIdForUsername(String username) {
        Optional<User> foundUsers = userRepository.findUserByUsername(username);

        return foundUsers(foundUsers);
    }


}
