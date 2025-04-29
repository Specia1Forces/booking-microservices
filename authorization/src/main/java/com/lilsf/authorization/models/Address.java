package com.lilsf.authorization.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String line;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    private Hotel hotel;
}
