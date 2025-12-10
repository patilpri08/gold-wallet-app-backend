package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "jewellery_items")
public class JewelleryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "jewelleryItem")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "jeweller_id", nullable = false)
    private Jeweller jeweller;

    private String type;                // Ring, Necklace, Bracelet, etc.
    private double weight;              // Weight in grams
    private String jewellery_condition;

    // New fields
    private String availability;        // e.g. "AVAILABLE, RENTED, MAINTENANCE"
    private String jewelleryCategory;   // e.g. "rings"
    private String photos;              // URL or path to photos (nullable)
    private String purity;              // e.g. "24k"
    private BigDecimal rentPerDay;      // Daily rent
    private BigDecimal rentPerWeekMonth;// Weekly/Monthly rent
    private BigDecimal replacementValue;// Replacement cost
    private BigDecimal securityDeposit; // Security deposit

    public JewelleryItem() {}

}