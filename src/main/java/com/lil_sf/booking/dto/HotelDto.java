package com.lil_sf.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    private int id;

    private String name;

    private String description;

    private String line;

    private String city;

    private String country;
}
