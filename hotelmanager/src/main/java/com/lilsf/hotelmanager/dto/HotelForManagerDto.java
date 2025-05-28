package com.lilsf.hotelmanager.dto;

import com.lilsf.hotelmanager.models.enums.CategoryType;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelForManagerDto {
    private int id;

    private String name;

    private String description;

    private boolean isActive;

    private CategoryType categoryType;

    private String line;

    private String city;

    private String country;
}
