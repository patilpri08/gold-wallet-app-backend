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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // ✅ not "id"
    private User customer;

    @ManyToOne
    @JoinColumn(name = "jewellery_id", nullable = false) // ✅ not "id"
    private JewelleryItem jewellery;

    private Long jewellerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
    private BigDecimal totalRent;

    public void setUserId(Long customerId) {
        customer.setId(customerId);
    }

    public Long getCustomerId() {
        return customer.getId();
    }

    public Long getJewelleryId() {
        return jewellery.getId();
    }
}