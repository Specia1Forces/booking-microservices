package com.lilsf.authorization.models;


import com.lilsf.authorization.models.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "rooms")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal currentPrice;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;


    @ManyToOne
    @JoinColumn(nullable = false)
    //@JoinColumn(name="hotel_id",referencedColumnName ="hotel_id" )
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookingList;

    @OneToMany(mappedBy = "room")
    private List<Unavailability> unavailabilityList;
}
