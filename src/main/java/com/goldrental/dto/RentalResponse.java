package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RentalResponse {

    private Long id;
    private Long userId;
    private Long jewelleryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rentalStatus;
    private BigDecimal totalRent;

    // ✅ New fields
    private String jewellerName;
    private String jewelleryCategory;

    // Constructors
    public RentalResponse() {
    }

    public RentalResponse(Long id, Long userId, Long jewelleryId,
                          LocalDate startDate, LocalDate endDate,
                          String rentalStatus, String totalRent,
                          String jewellerName, String jewelleryCategory) {
        this.id = id;
        this.userId = userId;
        this.jewelleryId = jewelleryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jewellerName = jewellerName;
        this.jewelleryCategory = jewelleryCategory;
    }

    @Override
    public String toString() {
        return "RentalResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", jewelleryId=" + jewelleryId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", rentalStatus=" + rentalStatus +
                ", totalRent='" + totalRent + '\'' +
                ", jewellerName='" + jewellerName + '\'' +
                ", jewelleryCategory='" + jewelleryCategory + '\'' +
                '}';
    }
}