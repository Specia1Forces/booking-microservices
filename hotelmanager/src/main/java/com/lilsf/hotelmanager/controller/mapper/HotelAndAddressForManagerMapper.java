package com.lilsf.hotelmanager.controller.mapper;

import com.lilsf.hotelmanager.dto.HotelDto;
import com.lilsf.hotelmanager.dto.HotelForManagerDto;
import com.lilsf.hotelmanager.dto.RoomDto;
import com.lilsf.hotelmanager.models.Address;
import com.lilsf.hotelmanager.models.Hotel;

public class HotelAndAddressForManagerMapper {
    public static Hotel mapHotelForManagerDtoToHotel(HotelDto dto) {
        return Hotel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(dto.isActive())
                .categoryType(dto.getCategoryType())
                .build();
    }

    public static Address mapHotelForManagerDtoToAddress(HotelDto dto) {
        return Address.builder()
                .line(dto.getLine())
                .city(dto.getCity())
                .country(dto.getCountry())
                .build();

    }
}
