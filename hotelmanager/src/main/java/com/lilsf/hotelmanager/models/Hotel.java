package com.lilsf.hotelmanager.models;



import com.lilsf.hotelmanager.models.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "hotels")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column( nullable = false)
    private String description;

    @Column( nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;


    @ManyToOne
    @JoinColumn(nullable = false)
    //@JoinColumn(name="hotel_manager_id",referencedColumnName ="hotel_manager_id" )
    private HotelManager hotelManager;

    @OneToMany(mappedBy = "hotel")
    private List<Room> roomList;

    @OneToOne
    //@JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @JoinColumn(nullable = false)
    private Address address;
    /*
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    */

}
