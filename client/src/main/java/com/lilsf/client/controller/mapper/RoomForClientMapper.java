package com.lilsf.client.controller.mapper;

import com.lilsf.client.dto.RoomForClientDto;
import com.lilsf.client.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomForClientMapper {

    public static RoomForClientDto mapRoomToRoomForClientDto(Room model) {
        return RoomForClientDto.builder()
                .clientId(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .currentPrice(model.getCurrentPrice())
                .roomType(model.getRoomType())
                .build();

    }

    public static List<RoomForClientDto> mapRoomToRoomForClientDto(List<Room> model) {
        List<RoomForClientDto> dtoList = new ArrayList<>();
        for (Room room : model) {
            dtoList.add(mapRoomToRoomForClientDto(room));
        }
        return dtoList;
    }
}
