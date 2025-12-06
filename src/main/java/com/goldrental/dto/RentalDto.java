package com.goldrental.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDto {
    private Long rentalId;
    private Long userId;
    private Long jewellerId;
    private Long inventoryItemId;

    private String itemName;
    private String itemType;
    private double weight;
    private BigDecimal dailyRentalRate;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal totalAmount;
    private String status;   // ACTIVE | RETURNED | OVERDUE

}
