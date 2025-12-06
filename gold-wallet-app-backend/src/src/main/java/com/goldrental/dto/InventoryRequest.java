package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventoryRequest {

    private Long jewellerId;      // Which jeweller owns this item
    private String name;          // Jewellery name
    private String type;          // Ring, Necklace, Bracelet, etc.
    private BigDecimal price;     // Total value in INR
    private BigDecimal dailyRentalRate; // Daily rental charge
    private double weight;        // Weight in grams
    private String status;        // AVAILABLE, RENTED, MAINTENANCE

    public InventoryRequest() {}

    public InventoryRequest(Long jewellerId, String name, String type, BigDecimal price,
                            BigDecimal dailyRentalRate, double weight, String status) {
        this.jewellerId = jewellerId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.dailyRentalRate = dailyRentalRate;
        this.weight = weight;
        this.status = status;
    }

}
