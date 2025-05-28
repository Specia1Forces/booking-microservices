package com.lilsf.hotelmanager.dto;

import com.lilsf.hotelmanager.models.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
