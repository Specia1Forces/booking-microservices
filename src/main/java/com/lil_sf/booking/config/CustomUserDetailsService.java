package com.lil_sf.booking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService { //CustomUserDetailsService implements UserDetailsService
    /*
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder().build();

            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();


        } else {
            throw new UsernameNotFoundException("Invalid username or password!");
        }

    }

    // доработать под несколько ролей
    private String getRoles(Users user) {
        return "HOTEL_MANAGER";
    }

     */
}
