package com.lil_sf.booking.service;


import com.lil_sf.booking.models.*;
import com.lil_sf.booking.models.enums.RoleType;
import com.lil_sf.booking.repositories.ClientRepository;
import com.lil_sf.booking.repositories.HotelManagerRepository;
import com.lil_sf.booking.repositories.RoleRepository;
import com.lil_sf.booking.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class

})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private HotelManagerRepository hotelManagerRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testFindOneRoom_withId_shouldReturnEmpty() {

        int hotelId = 0;
        int hotelManagerId = 0;
        int roomId = 0;

    }

    @Test
    void testCreateManagerUser_shouldReturnUser() {
        User repositoryUser = new User();
        repositoryUser.setUsername("newUser");
        repositoryUser.setPassword("password123");
        Role role = new Role();
        role.setRoleType(RoleType.HOTEL_MANAGER);


        when(userRepository.findUserByUsername(repositoryUser.getUsername())).thenReturn(Optional.empty());
        // Настройка мока для получения роли
        when(roleRepository.findByRoleType(RoleType.HOTEL_MANAGER)).thenReturn(role);
        // Настройка мока для кодирования пароля
        when(passwordEncoder.encode(repositoryUser.getPassword())).thenReturn("encodedPassword");

        // Вызов метода для создания менеджера гостиницы
        User saveUser = userService.createHotelManager(repositoryUser);

        // Проверка, что методы сохранения были вызваны
        verify(userRepository).save(repositoryUser);
        verify(hotelManagerRepository).save(any(HotelManager.class));

        // Проверка, что пароль был закодирован
        assertEquals("encodedPassword", saveUser.getPassword());
        assertEquals(role, saveUser.getRole());
    }

    @Test
    void testCreateClientUser_shouldReturnUser() {
        User repositoryUser = new User();
        repositoryUser.setUsername("newUser");
        repositoryUser.setPassword("password123");
        Role role = new Role();
        role.setRoleType(RoleType.CLIENT);

        when(userRepository.findUserByUsername(repositoryUser.getUsername())).thenReturn(Optional.empty());
        // Настройка мока для получения роли
        when(roleRepository.findByRoleType((RoleType.CLIENT))).thenReturn(role);
        // Настройка мока для кодирования пароля
        when(passwordEncoder.encode(repositoryUser.getPassword())).thenReturn("encodedPassword");

        // Вызов метода для создания менеджера гостиницы
        User saveUser = userService.createClient(repositoryUser);

        // Проверка, что методы сохранения были вызваны
        verify(userRepository).save(repositoryUser);
        verify(clientRepository).save(any(Client.class));

        // Проверка, что пароль был закодирован
        assertEquals("encodedPassword", saveUser.getPassword());
        assertEquals(role, saveUser.getRole());
    }
    @Test
    void testCreateHotelManager_shouldThrowException_whenUserExists() {
        User repositoryUser = new User();

        when(userRepository.findUserByUsername(repositoryUser.getUsername())).thenReturn(Optional.of(repositoryUser));

        assertThrows(RuntimeException.class, () -> userService.createHotelManager(repositoryUser));

    }

    @Test
    void testCreateClient_shouldThrowException_whenUserExists() {
        User repositoryUser = new User();

        when(userRepository.findUserByUsername(repositoryUser.getUsername())).thenReturn(Optional.of(repositoryUser));

        assertThrows(RuntimeException.class, () -> userService.createClient(repositoryUser));
    }
}
