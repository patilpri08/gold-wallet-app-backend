package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class RentalRequest {

    private Long userId;
    private Long jewellerId;
    private Long jewelleryId;      // Jewellery item to rent
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private BigDecimal rentalStatus;
    private BigDecimal rentalAmount;

    public RentalRequest() {}

}
