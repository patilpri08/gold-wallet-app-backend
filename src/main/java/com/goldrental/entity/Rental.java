package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ✅ primary key only

    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "jewellery_id", nullable = false) // ✅ not "id"
    private JewelleryItem jewellery;

    private Long jewellerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
    private BigDecimal totalRent;

    public Long getJewelleryId() {
        return jewellery.getId();
    }
}