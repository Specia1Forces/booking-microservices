package com.lilsf.client.dto;

import com.lilsf.client.models.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomForClientDto {
    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
