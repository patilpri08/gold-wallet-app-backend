package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class RentalRequest {

    private Long customerId;       // Customer who rents the jewellery
    private Long jewelleryId;      // Jewellery item to rent
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private BigDecimal rentalPrice;
    private BigDecimal rentalAmount;
    private BigDecimal rentalRate;

    public RentalRequest() {}

    public RentalRequest(Long customerId, Long jewelleryId, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.customerId = customerId;
        this.jewelleryId = jewelleryId;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

    public long getDays() {
        LocalDate start = this.rentalStartDate;
        LocalDate end = this.rentalEndDate;

        // Calculate difference in days
        long diffDays = ChronoUnit.DAYS.between(start, end);

        // Convert to BigDecimal
        BigDecimal days = BigDecimal.valueOf(diffDays);
        return days.longValue();
    }
}
