package com.goldrental.dto;

import com.goldrental.entity.JewelleryItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JewelleryInventoryResponse {

    private String category;
    private Double weight;
    private String purity;
    private BigDecimal dailyRental;
    private BigDecimal weeklyMonthlyRent;
    private BigDecimal securityDeposit;
    private String availability;
    private BigDecimal replacementValue;
    private String jewellery_condition;
    private String photos;

    // --- Default constructor ---
    public JewelleryInventoryResponse() {}

    // --- All-args constructor ---
    public JewelleryInventoryResponse(String category, Double weight, String purity,
                                      BigDecimal dailyRental, BigDecimal weeklyMonthlyRent, BigDecimal securityDeposit,
                                      String availability, BigDecimal replacementValue, String jewellery_condition) {

        this.category = category;
        this.weight = weight;
        this.purity = purity;
        this.dailyRental = dailyRental;
        this.weeklyMonthlyRent = weeklyMonthlyRent;
        this.securityDeposit = securityDeposit;
        this.availability = availability;
        this.replacementValue = replacementValue;
        this.jewellery_condition = jewellery_condition;
    }

    // --- Entity-mapping constructor ---
    public JewelleryInventoryResponse(JewelleryItem item) {

        this.category = item.getJewelleryCategory();
        this.weight = item.getWeight();
        this.purity = item.getPurity();
        this.dailyRental = item.getRentPerDay();
        this.weeklyMonthlyRent = item.getRentPerWeekMonth();
        this.securityDeposit = item.getSecurityDeposit();
        this.availability = item.getAvailability();
        this.replacementValue = item.getReplacementValue();
        this.jewellery_condition = item.getJewellery_condition();
        this.photos = item.getPhotos();
    }

    // --- Getters & Setters ---
    // (same as before)
}