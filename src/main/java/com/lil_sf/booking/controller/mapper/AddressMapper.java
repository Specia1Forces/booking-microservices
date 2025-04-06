package com.lil_sf.booking.controller.mapper;

import com.lil_sf.booking.dto.AddressDto;
import com.lil_sf.booking.models.Address;

public class AddressMapper {
    public static Address mapAddressDtoToAddress(AddressDto dto) {
        return Address.builder()
                .line(dto.getLine())
                .city(dto.getCity())
                .country(dto.getCountry())
                .build();
    }

    public static AddressDto mapAddressToAddressDto(Address address) {
        return AddressDto.builder()
                //.id(address.getId())
                .line(address.getLine())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}