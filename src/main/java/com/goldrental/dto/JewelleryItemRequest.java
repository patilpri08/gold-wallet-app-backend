package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JewelleryItemRequest {

    private Long jewellerId;              // Foreign key to Jeweller
    private Long userId;                  // Optional foreign key to User

    private String category;              // e.g. Rings, Necklaces
    private String description;           // Description of the jewellery
    private String occasion;              // Occasion (e.g. Wedding, Daily Wear)
    private List<String> types;           // e.g. ["Bridal Wear"]

    private Double weight;                // Weight in grams
    private String purity;                // e.g. "24k", "22k"
    private String hallmark;              // Hallmark number

    private BigDecimal valuationAmount;   // Valuation ₹
    private LocalDate valuationDate;      // Valuation date

    private BigDecimal blockAmount;       // Block amount ₹
    private BigDecimal replacementValue;  // Replacement cost ₹

    private Boolean insuranceCovered;     // true/false
    private String insuranceProvider;     // Insurance provider name

    private String condition;             // New, Excellent, Good, Used
    private String conditionNotes;        // Notes about condition
    private String resurface;             // Resurface info

    private String status;                // Available, Rented, etc.
    private String listingStatus;         // Listing status
    private LocalDate availableFrom;      // Available from date

    private String frontViewUrl;          // Path/URL to front view image
}