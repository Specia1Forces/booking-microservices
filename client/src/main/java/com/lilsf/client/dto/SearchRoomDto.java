package com.lilsf.client.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRoomDto {
    private Date startDate;

    private Date endDate;
}
