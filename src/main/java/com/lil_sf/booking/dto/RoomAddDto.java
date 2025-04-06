package com.lil_sf.booking.dto;

import com.lil_sf.booking.models.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAddDto {

    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
