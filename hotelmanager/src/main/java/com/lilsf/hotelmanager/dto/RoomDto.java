package com.lilsf.hotelmanager.dto;

import com.lilsf.hotelmanager.models.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
