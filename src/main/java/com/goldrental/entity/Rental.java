package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // primary key

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // explicit FK column
    private User user;

    @OneToOne
    @JoinColumn(name = "jewellery_id")
    private JewelleryInventory jewelleryItem;

    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
    private BigDecimal totalRent;
    private LocalDateTime createdDate;
    @Column(name = "security_blocked", precision = 19, scale = 2)
    private BigDecimal securityBlocked;


    private BigDecimal penalties = BigDecimal.ZERO;
    private BigDecimal paymentsMade = BigDecimal.ZERO;


}