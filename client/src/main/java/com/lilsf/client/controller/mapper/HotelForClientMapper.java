package com.lilsf.client.controller.mapper;



import com.lilsf.client.dto.HotelForClientDto;
import com.lilsf.client.models.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelForClientMapper {
    public static HotelForClientDto mapHotelToHotelForClientsDto(Hotel model) {
        return HotelForClientDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .categoryType(model.getCategoryType())
                .lineAddress(model.getAddress().getLine())
                .city(model.getAddress().getCity())
                .country(model.getAddress().getCountry())
                .build();

    }

    public static List<HotelForClientDto> mapHotelToHotelForClientsDto(List<Hotel> model) {

        List<HotelForClientDto> dtoList = new ArrayList<>();
        for (Hotel hotel : model) {
            dtoList.add(mapHotelToHotelForClientsDto(hotel));
        }
        return dtoList;
    }

}
