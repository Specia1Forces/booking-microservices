package com.lilsf.hotelmanager.controller.mapper;

import com.lilsf.hotelmanager.dto.HotelForManagerDto;
import com.lilsf.hotelmanager.models.Address;
import com.lilsf.hotelmanager.models.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelForManagerMapper {
    public static HotelForManagerDto mapHotelToHotelForClientsDto(Hotel model) {
        return HotelForManagerDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .isActive(model.isActive())
                .categoryType(model.getCategoryType())
                .line(model.getAddress().getLine())
                .city(model.getAddress().getCity())
                .country(model.getAddress().getCountry())
                .build();
    }

    public static List<HotelForManagerDto> mapHotelToHotelForClientsDto(List<Hotel> model) {

        List<HotelForManagerDto> dtoList = new ArrayList<>();
        for (Hotel hotel : model) {
            dtoList.add(mapHotelToHotelForClientsDto(hotel));
        }
        return dtoList;
    }
    


}
