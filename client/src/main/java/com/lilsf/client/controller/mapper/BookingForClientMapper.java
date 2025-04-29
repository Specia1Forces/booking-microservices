package com.lilsf.client.controller.mapper;


import com.lilsf.client.dto.BookingForClientDto;
import com.lilsf.client.dto.RoomForClientDto;
import com.lilsf.client.models.Booking;
import com.lilsf.client.models.Room;

import java.util.ArrayList;
import java.util.List;


public class BookingForClientMapper {
    public static BookingForClientDto mapBookingToBookingForClientDto(Booking model) {
        return BookingForClientDto.builder()
                .id(model.getId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .discountPercent(model.getDiscountPercent())
                .totalPrice(model.getTotalPrice())
                .hotel(model.getRoom().getHotel().getName())
                .name(model.getRoom().getName())
                .description(model.getRoom().getDescription())
                .currentPrice(model.getRoom().getCurrentPrice())
                .roomType(model.getRoom().getRoomType())
                .build();

    }

    public static List<BookingForClientDto> mapBookingToBookingForClientDto(List<Booking> model) {
        List<BookingForClientDto> dtoList = new ArrayList<>();
        for (Booking booking : model) {
            dtoList.add(mapBookingToBookingForClientDto(booking));
        }
        return dtoList;

    }

}
