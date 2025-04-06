package com.lil_sf.booking.service;

import com.lil_sf.booking.models.Client;
import com.lil_sf.booking.models.HotelManager;
import com.lil_sf.booking.models.Role;
import com.lil_sf.booking.models.User;
import com.lil_sf.booking.models.enums.RoleType;
import com.lil_sf.booking.repositories.HotelManagerRepository;
import com.lil_sf.booking.repositories.RoleRepository;
import com.lil_sf.booking.repositories.ClientRepository;
import com.lil_sf.booking.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final HotelManagerRepository hotelManagerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ClientRepository clientRepository, HotelManagerRepository hotelManagerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.hotelManagerRepository = hotelManagerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createClient(User user) {

        Optional<User> foundUsers = userRepository.findUserByUsername(user.getUsername());
        if (foundUsers.isPresent()) {
            throw new RuntimeException("The user exists, use a different username");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleType(RoleType.CLIENT);
        user.setRole(role);

        Client client = new Client();
        client.setUser(user);
        userRepository.save(user);
        clientRepository.save(client); //порядок может быть нарушен

        return user;

    }


    public User createHotelManager(User user) {
        Optional<User> foundUsers = userRepository.findUserByUsername(user.getUsername());
        if (foundUsers.isPresent()) {
            throw new RuntimeException("The user exists, use a different username");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleType(RoleType.HOTEL_MANAGER);
        user.setRole(role);

        HotelManager hotelManager = new HotelManager();
        hotelManager.setUser(user);
        userRepository.save(user);
        hotelManagerRepository.save(hotelManager); //порядок может быть нарушен

        return user;
    }
}
