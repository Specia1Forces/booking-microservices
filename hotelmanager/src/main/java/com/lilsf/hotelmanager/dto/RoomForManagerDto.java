package com.lilsf.hotelmanager.dto;

import com.lilsf.hotelmanager.models.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomForManagerDto {
    private int roomId;

    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
