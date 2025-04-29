package com.lilsf.client.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "hotel_managers")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "hotelManager")
    private List<Hotel> hotelList;
}
