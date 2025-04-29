package com.lilsf.hotelmanager.models;


import com.lilsf.hotelmanager.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    //Доделать!!!!!!!!!!!!!!
    @CreationTimestamp
    private LocalDateTime created;

    @CreationTimestamp //скорее всего update
    private LocalDateTime updated;

    @Column(nullable = false)
    private BigDecimal discountPercent;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    //@JoinColumn(name = "booking_client_id", referencedColumnName = "client_id")
    @JoinColumn(nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(nullable = false)
    //@JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room room;


}
