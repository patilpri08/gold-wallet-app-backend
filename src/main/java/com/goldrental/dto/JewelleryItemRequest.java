package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;

@Getter
@Setter
public class JewelleryItemRequest {

    private String jewellerId;          // Foreign key to Jeweller

    private String name;                // Name of the jewellery item
    private String jewelleryCategory;   // Category field (e.g., rings, necklaces)

    private Double weight;              // Weight in grams
    private String purity;              // e.g. "24k", "22k"

    private BigDecimal dailyRent;       // Daily rent (if separate from rate)
    private BigDecimal dailyRentalRate; // Daily rental charge
    private BigDecimal rentPerWeekMonth;// Weekly/Monthly rent

    private BigDecimal replacementValue;// Replacement cost
    private BigDecimal securityDeposit; // Security deposit

    private String status;              // AVAILABLE, RENTED, MAINTENANCE
    private String availability;       // true = available, false = not available
    private String jewellery_condition;
    private String jeweller_id;// Condition description

}