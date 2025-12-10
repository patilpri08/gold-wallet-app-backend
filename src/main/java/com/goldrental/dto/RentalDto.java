package com.goldrental.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RentalDto {
    private Long id;
    private Long user_id;
    private Long jewelleryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
    private BigDecimal totalRent;   // ACTIVE | RETURNED | OVERDUE

}
