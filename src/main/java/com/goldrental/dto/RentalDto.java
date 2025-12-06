package com.goldrental.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDto {
    private Long id;
    private Long user_id;
    private Long jewellerId;
    private Long jewelleryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal rentalStatus;
    private String totalRent;   // ACTIVE | RETURNED | OVERDUE

}
