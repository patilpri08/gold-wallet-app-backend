package com.goldrental.dto;

import com.goldrental.entity.JewelleryInventory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JewelleryInventoryResponse {

    private Long jewelleryId;
    private Long jewellerId;
    private Long userId;

    private String category;
    private String description;
    private String occasion;
    private List<String> types;

    private Double weight;
    private String purity;
    private String hallmark;

    private BigDecimal valuationAmount;
    private LocalDate valuationDate;

    private BigDecimal blockAmount;
    private BigDecimal replacementValue;

    private Boolean insuranceCovered;
    private String insuranceProvider;

    private String condition;
    private String conditionNotes;
    private String resurface;

    private String status;
    private String listingStatus;
    private LocalDate availableFrom;

    private String frontViewUrl;

    // --- Default constructor ---
    public JewelleryInventoryResponse() {}

    // --- Entity-mapping constructor ---
    public JewelleryInventoryResponse(JewelleryInventory item) {
        this.jewelleryId = item.getId();
        this.jewellerId = item.getJeweller() != null ? item.getJeweller().getId() : null;
        this.userId = item.getJewelleryItemUser() != null ? item.getJewelleryItemUser().getId() : null;

        this.category = item.getCategory();
        this.description = item.getDescription();
        this.occasion = item.getOccasion();
        this.types = item.getTypes();

        this.weight = item.getWeight();
        this.purity = item.getPurity();
        this.hallmark = item.getHallmark();

        this.valuationAmount = item.getValuationAmount() != null ? item.getValuationAmount() : null;
        this.valuationDate = item.getValuationDate();

        this.blockAmount = item.getBlockAmount() != null ? item.getBlockAmount() : null;
        this.replacementValue = item.getReplacementValue() != null ? item.getReplacementValue() : null;

        this.insuranceCovered = item.getInsuranceCovered();
        this.insuranceProvider = item.getInsuranceProvider();

        this.condition = item.getJewellery_condition();
        this.conditionNotes = item.getConditionNotes();
        this.resurface = item.getResurface();

        this.status = item.getStatus();
        this.listingStatus = item.getListingStatus();
        this.availableFrom = item.getAvailableFrom();

        this.frontViewUrl = item.getFrontViewUrl();
    }
}