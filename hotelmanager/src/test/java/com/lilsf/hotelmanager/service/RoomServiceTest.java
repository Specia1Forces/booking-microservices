package com.lilsf.hotelmanager.service;


import com.lilsf.hotelmanager.models.Hotel;
import com.lilsf.hotelmanager.models.HotelManager;
import com.lilsf.hotelmanager.models.Room;
import com.lilsf.hotelmanager.models.User;
import com.lilsf.hotelmanager.repositories.HotelManagerRepository;
import com.lilsf.hotelmanager.repositories.HotelRepository;
import com.lilsf.hotelmanager.repositories.RoomRepository;
import com.lilsf.hotelmanager.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = {RoomService.class

})
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private HotelManagerRepository hotelManagerRepository;
    @MockBean
    private UserRepository userRepository;
    /*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roomService = new RoomService(hotelRepository, roomRepository, hotelManagerRepository, userRepository);
    }
     */

    @Test
    void testFindOneRoom_withId_shouldReturnEmpty() {

        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;
        Optional<Hotel> hotel = Optional.of(new Hotel());
        Room repositoryRoom = new Room();
        repositoryRoom.setId(roomId);

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(hotel);
        when(roomRepository.findRoomByHotel_IdAndId(hotelId, hotelManagerId)).thenReturn(Optional.of(repositoryRoom));

        Room room = roomService.findOne(hotelId, hotelManagerId, roomId);
        room.setId(2);

        assertEquals(repositoryRoom, room);

        /*
        // имитируем ситуацию когда нет машин в бд
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        var clients = carService.getAllCarsByClientId(UUID.randomUUID());

        // сервис ничего не возвращает, так как репозиторий пуст
        assertTrue(clients.isEmpty());

         */
    }

    @Test
    void testFindOneRoom_shouldReturnRoom_whenRoomFound() {
        int hotelManagerId = 1;
        int hotelId = 1;
        int roomId = 1;

        // Настройка мока для случая, когда отель принадлежит менеджеру
        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.of(new Hotel()));

        // Настройка мока для существующей комнаты
        Room room = new Room();
        room.setId(roomId);
        when(roomRepository.findRoomByHotel_IdAndId(hotelId, roomId)).thenReturn(Optional.of(room));

        // Вызов метода для поиска комнаты
        Room foundRoom = roomService.findOne(hotelManagerId, hotelId, roomId);

        // Проверка, что комната была найдена
        assertNotNull(foundRoom);
        assertEquals(roomId, foundRoom.getId());
    }

    @Test
    void testFindOneRoom_shouldReturnRoom_whenRoomNotFound() {
        int hotelManagerId = 1;
        int hotelId = 1;
        int roomId = 1;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.of(new Hotel()));

        // Настройка мока для отсутствующей комнаты
        when(roomRepository.findRoomByHotel_IdAndId(hotelId, roomId)).thenReturn(Optional.empty());

        // Вызов метода для поиска комнаты
        Room foundRoom = roomService.findOne(hotelManagerId, hotelId, roomId);

        // Проверка, что комната не найдена (должен вернуть null)
        assertNull(foundRoom);
    }
    @Test
    void testFindAllRooms_shouldReturnRoom_whenRoomFound() {

        int hotelManagerId = 1;
        int hotelId = 1;

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId))
                .thenReturn(Optional.of(hotel));

        Room room1 = new Room();
        room1.setId(1); // Установите необходимое значение
        Room room2 = new Room();
        room2.setId(2);
        when(roomRepository.findRoomByHotel_id(hotelId))
                .thenReturn(List.of(room1, room2));

        // Act
        List<Room> rooms = roomService.findAll(hotelManagerId, hotelId);

        // Assert
        assertNotNull(rooms);
        assertEquals(2, rooms.size());
        assertEquals(room1.getId(), rooms.get(0).getId());
        assertEquals(room2.getId(), rooms.get(1).getId());
    }

    @Test
    void testFindAllRooms_shouldReturnRoom_whenRoomNotFound() {
        int hotelManagerId = 1;
        int hotelId = 1;

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId))
                .thenReturn(Optional.of(hotel));


        when(roomRepository.findRoomByHotel_id(hotelId))
                .thenReturn(null);

        // Act
        List<Room> rooms = roomService.findAll(hotelManagerId, hotelId);

        // Assert
        assertNull(rooms);
    }

    @Test
    void testSaveRoom_shouldSaveRoom_whenRoomSaved() {
        int hotelManagerId = 1;
        int hotelId = 1;
        Room room = new Room();

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId))
                .thenReturn(Optional.of(hotel));

        when(roomRepository.save(any(Room.class))).thenReturn(room); // Мокаем сохранение комнаты


        Room savedRoom = roomService.save(hotelManagerId, hotelId, room);


        assertNotNull(savedRoom);
        assertEquals(hotel, savedRoom.getHotel()); // Проверяем, что комната связана с отелем
        verify(roomRepository).save(room); // Проверяем, что метод save был вызван
    }

    @Test
    void testDeleteRoom_shouldDeleteRoom_whenRoomDeleted() {
        // Arrange
        int hotelManagerId = 1;
        int hotelId = 1;
        int roomId = 1;
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId))
                .thenReturn(Optional.of(hotel));

        roomService.deleteRoom(hotelManagerId, hotelId, roomId);


        verify(roomRepository).deleteById(roomId); // Проверяем, что метод deleteById был вызван
    }

    @Test
    void testUpdateRoom_shouldUpdateRoom_whenRoomUpdated() {
        int hotelManagerId = 1;
        int hotelId = 1;
        int roomId = 1;

        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId))
                .thenReturn(Optional.of(hotel));

        Room updatedRoom = new Room();
        updatedRoom.setHotel(hotel); // Установка отеля для обновления

        // Act
        roomService.update(hotelManagerId, hotelId, roomId, updatedRoom);

        // Assert
        verify(roomRepository).save(updatedRoom); // Проверяем, что метод save был вызван
        assertEquals(roomId, updatedRoom.getId()); // Проверяем, что ID обновленного номера установлен правильно
    }

    @Test
    void testFindOne_shouldThrowException_whenHotelNotFound() {
        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.findOne(hotelId, hotelManagerId, roomId));
    }

    @Test
    void testFindAll_shouldThrowException_whenHotelNotFound() {
        int hotelId = 0;
        int hotelManagerId = 0;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.findAll(hotelManagerId, hotelId));
    }


    @Test
    void testSave_shouldThrowException_whenHotelNotFound() {
        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;
        Room room = new Room();

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.save(hotelManagerId, hotelId, room));
    }

    @Test
    void testDeleteById_shouldThrowException_whenHotelNotFound() {
        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.deleteRoom(hotelManagerId, hotelId, roomId));
    }

    @Test
    void testUpdate_shouldThrowException_whenHotelNotFound() {
        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;
        Room room = new Room();

        when(hotelRepository.findHotelByIdAndHotelManager_Id(hotelId, hotelManagerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.update(hotelManagerId, hotelId, roomId, room));
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
        int managerId = roomService.getCurrentManagerIdForUsername("existingUser");

        // Проверка результата
        assertEquals(hotelManager.getId(), managerId);
    }

    @Test
    void testGetCurrentManagerIdForUsername_shouldThrowException_whenUserNotFound() {
        String username = "test";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.getCurrentManagerIdForUsername(username));
    }

    @Test
    void testGetCurrentManagerIdForUsername_shouldThrowException_whenHotelManagerNotFound() {
        String username = "test";
        int userId = 0;
        User user = new User();

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        when(hotelManagerRepository.findHotelManagerByUser_Id(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.getCurrentManagerIdForUsername(username));
    }

}
