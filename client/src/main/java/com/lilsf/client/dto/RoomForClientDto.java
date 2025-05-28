package com.lilsf.client.dto;

import com.lilsf.client.models.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomForClientDto {
    private int clientId;

    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;
}
