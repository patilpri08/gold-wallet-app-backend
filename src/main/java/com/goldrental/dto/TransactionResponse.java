package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private OffsetDateTime txnDate;
    private String type;
    private String jewelleryName;
    private String duration;
    private BigDecimal amount;
    private String mode;
    private String status;

    public TransactionResponse(Long id,
                               OffsetDateTime txnDate,
                               String type,
                               String jewelleryName,
                               String duration,
                               BigDecimal amount,
                               String mode,
                               String status) {
        this.id = id;
        this.txnDate = txnDate;
        this.type = type;
        this.jewelleryName = jewelleryName;
        this.duration = duration;
        this.amount = amount;
        this.mode = mode;
        this.status = status;
    }

    public TransactionResponse(Long id,
                               OffsetDateTime txnDate,
                               String type,
                               String jewelleryName,
                               String duration,
                               BigDecimal amount,
                               String mode) {
        this.id = id;
        this.txnDate = txnDate;
        this.type= type;
        this.jewelleryName = jewelleryName;
        this.duration = duration;
        this.amount = amount;
        this.mode = mode;
    }

    // getters/setters
}