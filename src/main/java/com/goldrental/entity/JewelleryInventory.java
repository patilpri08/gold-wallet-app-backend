package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "jewellery_inventory")
public class JewelleryInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "jewelleryItem")
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "jeweller_id", nullable = false)
    private Jeweller jeweller;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User jewelleryItemUser;

    private LocalDate availableFrom;

    private BigDecimal blockAmount;

    private String category;

    private String jewellery_condition;

    @Column(columnDefinition = "TEXT")
    private String conditionNotes;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String hallmark;

    // Store image URL instead of file blob for scalability
    private String frontViewUrl;

    private Boolean insuranceCovered;

    private String insuranceProvider;

    private String listingStatus;

    private String occasion;

    private String purity;

    private BigDecimal replacementValue;

    private String resurface;

    private String status;

    @Column(name = "types")
    private List<String> types;

    private BigDecimal valuationAmount;

    private LocalDate valuationDate;

    private Double weight;

    @Column(updatable = false)
    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt = LocalDate.now();


    public JewelleryInventory() {}

}