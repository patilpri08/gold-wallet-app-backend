package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentalRequest {

    private Long customerId;       // Customer who rents the jewellery
    private Long jewelleryId;      // Jewellery item to rent
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    public RentalRequest() {}

    public RentalRequest(Long customerId, Long jewelleryId, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.customerId = customerId;
        this.jewelleryId = jewelleryId;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }

}
