package com.lilsf.hotelmanager.controller.mapper;

import com.lilsf.hotelmanager.dto.RoomDto;
import com.lilsf.hotelmanager.dto.RoomForManagerDto;
import com.lilsf.hotelmanager.models.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomForManagerMapper {
    public static RoomForManagerDto mapRoomToRoomForManagerDto(Room model) {
        return RoomForManagerDto.builder()
                .roomId(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .currentPrice(model.getCurrentPrice())
                .roomType(model.getRoomType())
                .build();
    }

    public static List<RoomForManagerDto> mapRoomToRoomForManagerDto(List<Room> modelList) {
        List<RoomForManagerDto> dtoList = new ArrayList<>();
        for (Room room : modelList) {
            dtoList.add(mapRoomToRoomForManagerDto(room));
        }
        return dtoList;
    }

    public static Room mapRoomForManagerDtoToRoom(RoomDto dto) {
        return Room.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .currentPrice(dto.getCurrentPrice())
                .roomType(dto.getRoomType())
                .build();
    }
}
