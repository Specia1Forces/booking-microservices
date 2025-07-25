package com.lilsf.client.dto;

import com.lilsf.client.models.enums.BookingStatus;
import com.lilsf.client.models.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingForClientDto {
    private int id;

    private Date startDate;

    private Date endDate;

    private BigDecimal discountPercent;

    private BookingStatus bookingStatus;

    private BigDecimal totalPrice;

    private String hotel;

    private String name;

    private String description;

    private BigDecimal currentPrice;

    private RoomType roomType;

}
