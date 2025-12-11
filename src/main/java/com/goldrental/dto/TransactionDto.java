package com.goldrental.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class TransactionDto {
    private String id;            // txnId
    private OffsetDateTime date;  // keep as OffsetDateTime to match entity
    private String type;
    private String jewellery;
    private String duration;
    private BigDecimal amount;
    private String mode;

    // Constructor matching JPQL: (String, OffsetDateTime, String, String, String, BigDecimal, String)
    public TransactionDto(String id,
                          OffsetDateTime date,
                          String type,
                          String jewellery,
                          String duration,
                          BigDecimal amount,
                          String mode) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.jewellery = jewellery;
        this.duration = duration;
        this.amount = amount;
        this.mode = mode;
    }

    // Optional: constructor that accepts date as String if you use FUNCTION('TO_CHAR', ...)
    public TransactionDto(String id,
                          String date,
                          String type,
                          String jewellery,
                          String duration,
                          BigDecimal amount,
                          String mode) {
        this.id = id;
        // convert string to OffsetDateTime if needed, or store raw string in a separate field
        // here we keep a null-safe approach: leave date null and store string elsewhere if required
        this.type = type;
        this.jewellery = jewellery;
        this.duration = duration;
        this.amount = amount;
        this.mode = mode;
    }
}