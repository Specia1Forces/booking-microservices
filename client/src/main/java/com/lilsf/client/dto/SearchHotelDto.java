package com.lilsf.client.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHotelDto {

    private String city;

    private Date startDate;

    private Date endDate;
}
