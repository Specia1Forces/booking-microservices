package com.lilsf.hotelmanager.service;



import com.lilsf.hotelmanager.models.Hotel;
import com.lilsf.hotelmanager.models.HotelManager;
import com.lilsf.hotelmanager.models.Room;
import com.lilsf.hotelmanager.models.User;
import com.lilsf.hotelmanager.repositories.HotelManagerRepository;
import com.lilsf.hotelmanager.repositories.HotelRepository;
import com.lilsf.hotelmanager.repositories.RoomRepository;
import com.lilsf.hotelmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomService {
    @Autowired
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelManagerRepository hotelManagerRepository;
    private final UserRepository userRepository;

    public RoomService(HotelRepository hotelRepository, RoomRepository roomRepository, HotelManagerRepository hotelManagerRepository, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.hotelManagerRepository = hotelManagerRepository;
        this.userRepository = userRepository;
    }


    public Room findOne(int hotelManagerId, int hotelId, int roomId) {
        //Integer hotelManagerId = 0;// id владельца отеля
        // проверка принадлежит ли отель владельцу
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
        if (foundHotel.isEmpty()) {
            throw new RuntimeException("The hotel does not belong to the manager");
        }

        Optional<Room> foundRoom = roomRepository.findRoomByHotel_IdAndId(hotelId, roomId);
        return foundRoom.orElse(null);
    }

    public List<Room> findAll(int hotelManagerId, int hotelId) {
        //Integer hotelManagerId = 0;// id владельца отеля
        // проверка принадлежит ли отель владельцу
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
        if (foundHotel.isEmpty()) {
            throw new RuntimeException("The hotel does not belong to the manager");
        }

        return roomRepository.findRoomByHotel_id(hotelId);
    }

    @Transactional
    public Room save(int hotelManagerId, int hotelId, Room room) {
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
        if (foundHotel.isEmpty()) {
            throw new RuntimeException("The hotel does not belong to the manager");
        }
        room.setHotel(foundHotel.get());

        //дописать нужные сеттеры (id владельца)
        //Integer hotelManagerId = 0;
        //hotel.setHotelManager();
        // проверка приннадлежит ли отель владельцу
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(int hotelManagerId, int hotelId, int id) {
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
        if (foundHotel.isEmpty()) {
            throw new RuntimeException("The hotel does not belong to the manager");
        }
        //Integer hotelManagerId = 0;// id владельца отеля
        // проверять если ли номер -> исключение
        roomRepository.deleteById(id);
    }

    @Transactional
    public Room update(int hotelManagerId, int hotelId, int id, Room updatedRoom) {
        Optional<Hotel> foundHotel = hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
        if (foundHotel.isEmpty()) {
            throw new RuntimeException("The hotel does not belong to the manager");
        }
        //  проверка на владельца
        updatedRoom.setId(id);
        updatedRoom.setHotel(updatedRoom.getHotel());
        return roomRepository.save(updatedRoom);
        // исправить нужно сперва найти старое значение

    }


    public int getCurrentManagerId() {
        Optional<User> foundUsers = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return foundUsers(foundUsers);

    }

    private int foundUsers(Optional<User> foundUsers) {
        if (foundUsers.isEmpty()) {
            throw new RuntimeException("The user was not found");
        }
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
