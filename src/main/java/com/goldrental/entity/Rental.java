package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rentals")
public class Rental {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jewellery_id")
    private Jewellery jewellery_id;
    
    private BigDecimal total_rent;
    private Long jeweller_id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
}
