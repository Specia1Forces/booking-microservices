package com.lilsf.client.dto;

import com.lilsf.client.models.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelForClientDto {

    private int id;

    private String name;

    private String description;

    private CategoryType categoryType;

    private String lineAddress;

    private String city;

    private String country;

}
