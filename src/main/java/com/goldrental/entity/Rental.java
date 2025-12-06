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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Link to User (customer)
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User customer;

    // ✅ Link to JewelleryItem
    @ManyToOne
    @JoinColumn(name = "jewellery_id", nullable = false)
    private JewelleryItem jewellery;

    private BigDecimal totalRent;

    private LocalDate startDate;

    private LocalDate endDate;

    private String rentalStatus;

    private Long userId;

    public Long getJewelleryId() {
        return jewellery.getId();
    }

    public Long getCustomerId() {
        return customer.getId();
    }
}