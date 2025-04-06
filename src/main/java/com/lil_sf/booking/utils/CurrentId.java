package com.lil_sf.booking.utils;

public class CurrentId {
    public static String funcOne(String str) {
        return "An apple a day, keeps doctor away " + str + "!";
    }
    /*
    private int getCurrentManagerId() {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> foundUsers = userRepository.findUserByUsername(username);
        return hotelManagerRepository.findHotelManagerByHotelManagerId_HotelManagerId(foundUsers.get().getUserId()).get().getHotelManagerId();

    }
     */
}
