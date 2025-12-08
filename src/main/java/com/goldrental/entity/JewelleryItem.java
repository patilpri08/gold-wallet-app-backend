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

    @ManyToOne
    @JoinColumn(name = "jeweller_id", nullable = false)
    private Jeweller jeweller;

    private String name;                // Name of the jewellery item
    private String type;                // Ring, Necklace, Bracelet, etc.
    private BigDecimal price;           // Total value in INR
    private BigDecimal dailyRentalRate; // Daily rental charge
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

    // Extended constructor
    public JewelleryItem(String name, String type, BigDecimal price, BigDecimal dailyRentalRate,
                         double weight, String status, Jeweller jeweller,
                         String availability, String jewellery_condition, String jewelleryCategory,
                         String photos, String purity, BigDecimal rentPerDay,
                         BigDecimal rentPerWeekMonth, BigDecimal replacementValue,
                         BigDecimal securityDeposit) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.dailyRentalRate = dailyRentalRate;
        this.weight = weight;
        this.jeweller = jeweller;

        this.availability = availability;
        this.jewellery_condition = jewellery_condition;
        this.jewelleryCategory = jewelleryCategory;
        this.photos = photos;
        this.purity = purity;
        this.rentPerDay = rentPerDay;
        this.rentPerWeekMonth = rentPerWeekMonth;
        this.replacementValue = replacementValue;
        this.securityDeposit = securityDeposit;
    }
}