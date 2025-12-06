package main.java.com.goldrental.dto;

import java.time.LocalDate;

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

    // --- Getters and Setters ---

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getJewelleryId() {
        return jewelleryId;
    }

    public void setJewelleryId(Long jewelleryId) {
        this.jewelleryId = jewelleryId;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public Long getItemId() {
        return this.jewelleryId;
    }

    public Long getUserId() {
        return this.customerId;
    }

    public long getDays() {
        return  rentalEndDate.getDayOfMonth() - rentalStartDate.getDayOfMonth();
    }
}
