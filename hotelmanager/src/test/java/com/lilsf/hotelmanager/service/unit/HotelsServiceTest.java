package com.lilsf.hotelmanager.service.unit;


import com.lilsf.hotelmanager.models.Address;
import com.lilsf.hotelmanager.models.Hotel;
import com.lilsf.hotelmanager.models.HotelManager;
import com.lilsf.hotelmanager.models.User;
import com.lilsf.hotelmanager.repositories.AddressRepository;
import com.lilsf.hotelmanager.repositories.HotelManagerRepository;
import com.lilsf.hotelmanager.repositories.HotelRepository;
import com.lilsf.hotelmanager.repositories.UserRepository;
import com.lilsf.hotelmanager.service.HotelsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {HotelsService.class

})
class HotelsServiceTest {

    @Autowired
    private HotelsService hotelsService;

    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private HotelManagerRepository hotelManagerRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AddressRepository addressRepository;

    @Test
    void testFindOne_shouldReturnHotel_whenHotelIsFound() {
        List<Hotel> repositoryHotels = getHotels(2);
        int id = 1;
        int hotelManagerId = 1;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(id, hotelManagerId)).thenReturn(Optional.ofNullable(repositoryHotels.get(0)));

        Hotel findHotel = hotelsService.findOne(hotelManagerId, id);
        assertNotNull(findHotel);
        assertEquals(repositoryHotels.get(0), findHotel);
    }

    @Test
    void testFindOne_shouldReturnHotel_whenHotelIsNotFound() {
        int id = 1;
        int hotelManagerId = 1;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(id, hotelManagerId)).thenReturn(Optional.empty());

        Hotel findHotel = hotelsService.findOne(hotelManagerId, id);
        assertNull(findHotel);
    }

    @Test
    void testFindAll_shouldReturnHotels_whenHotelsIsFound() {
        List<Hotel> repositoryHotels = getHotels(5);
        int hotelManagerId = 1;

        when(hotelRepository.findHotelByHotelManager_Id(hotelManagerId)).thenReturn(repositoryHotels);

        List<Hotel> hotels = hotelsService.findAll(hotelManagerId);

        assertNotNull(hotels);
        assertEquals(repositoryHotels, hotels);

    }

    @Test
    void testSaveHotel_shouldReturnHotel_whenHotelIsSaved() {

        int hotelManagerId = 1;

        Hotel hotel = new Hotel();
        Address address = new Address();
        HotelManager hotelManager = new HotelManager();


        // Настройка моков
        when(hotelManagerRepository.findHotelManagerByUser_Id(hotelManagerId)).thenReturn(Optional.of(hotelManager));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // Вызов метода
        Hotel savedHotel = hotelsService.save(hotelManagerId, hotel, address);

        // Проверка взаимодействий с моками
        verify(addressRepository).save(address);
        verify(hotelManagerRepository).findHotelManagerByUser_Id(hotelManagerId);
        verify(hotelRepository).save(hotel);

        // Проверка состояния сохраненного объекта

        assertEquals(hotelManager, savedHotel.getHotelManager());
        assertEquals(address, savedHotel.getAddress());
        assertSame(hotel, savedHotel);

    }

    @Test
    void testDeleteHotel_shouldDeleteHotel_whenHotelIsDeleted(){
        int hotelManagerId = 1;
        int hotelId = 10;

        hotelsService.deleteById(hotelManagerId, hotelId);

        // Проверка взаимодействия с моками
        verify(hotelRepository).deleteHotelByIdAndHotelManager_Id(hotelId, hotelManagerId);
    }

    @Test
    void testFindAll_shouldReturnHotels_whenHotelsIsNotFound() {
        List<Hotel> repositoryHotels = null;
        int hotelManagerId = 1;

        when(hotelRepository.findHotelByHotelManager_Id(hotelManagerId)).thenReturn(repositoryHotels);

        assertNull(hotelsService.findAll(hotelManagerId));
    }

    @Test
    void testUpdateHotel_shouldReturnHotel_whenHotelIsUpdated() {
        int hotelManagerId = 1;
        int hotelId = 10;

        Hotel updatedHotel = new Hotel();
        HotelManager hotelManager = new HotelManager();
        hotelManager.setId(hotelManagerId);
        updatedHotel.setHotelManager(hotelManager);
        Address updatedAddress = new Address();

        // Настройка мока для сохранения гостиницы
        when(hotelRepository.save(any(Hotel.class))).thenReturn(updatedHotel);

        // Вызов метода
        Hotel result = hotelsService.update(hotelManagerId, hotelId, updatedHotel, updatedAddress);

        // Проверка взаимодействия с моками
        verify(addressRepository).save(updatedAddress);
        verify(hotelRepository).save(updatedHotel);

        // Проверка результата
        assertEquals(updatedHotel, result);
        assertEquals(hotelId, result.getId());
        assertEquals(updatedAddress, result.getAddress());
    }

    @Test
    void testUpdateHotel_shouldReturnHotel_whenHotelIsNotFound() {

        int hotelManagerId = 1;
        int otherHotelManagerId = 2;
        int hotelId = 10;

        Hotel updatedHotel = new Hotel();
        HotelManager hotelManager = new HotelManager();
        hotelManager.setId(hotelManagerId);
        updatedHotel.setHotelManager(hotelManager);
        Address updatedAddress = new Address();



        // Настройка мока для сохранения гостиницы
        when(hotelRepository.save(any(Hotel.class))).thenReturn(updatedHotel);


        // Проверка результата
        assertThrows(IllegalArgumentException.class, () -> hotelsService.update(otherHotelManagerId, hotelId, updatedHotel, updatedAddress));
    }

    @Test
    void testGetCurrentManager_shouldReturnUser_whenUserIsFound() {
        User user = new User();
        user.setId(1); // Присваиваем ID пользователю
        HotelManager hotelManager = new HotelManager();
        hotelManager.setId(2);

        // Настройка мока для существующего пользователя
        when(userRepository.findUserByUsername("existingUser")).thenReturn(Optional.of(user));
        // Настройка мока для существующего менеджера гостиницы
        when(hotelManagerRepository.findHotelManagerByUser_Id(user.getId())).thenReturn(Optional.of(hotelManager));

        // Вызов метода и проверка результата
        int managerId = hotelsService.getCurrentManagerIdForUsername("existingUser");

        // Проверка результата
        assertEquals(hotelManager.getId(), managerId);
    }

    @Test
    void testGetCurrentManagerIdForUsername_shouldThrowException_whenUserNotFound() {
        String username = "test";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> hotelsService.getCurrentManagerIdForUsername(username));
    }


    @Test
    void testGetCurrentManagerIdForUsername_shouldThrowException_whenHotelManagerNotFound() {
        String username = "test";
        int userId = 0;
        User user = new User();

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        when(hotelManagerRepository.findHotelManagerByUser_Id(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> hotelsService.getCurrentManagerIdForUsername(username));
    }

    private List<Hotel> getHotels(int count) {
        List<Hotel> hotels = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            hotels.add(Hotel.builder()
                    .id(i)
                    .name("Hotel " + i)
                    .description("description about hotel " + i)
                    .isActive(false)
                    .categoryType(null)
                    .hotelManager(new HotelManager())
                    .roomList(new ArrayList<>())
                    .address(new Address()).build());
        }
        return hotels;
    }

    private List<Address> getAddresses(int count) {
        List<Address> addresses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            addresses.add(Address.builder()
                    .id(i)
                    .line("street " + i)
                    .city("city " + i)
                    .country("country " + i)
                    .build()
            );
        }
        return addresses;
    }

}
