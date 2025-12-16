package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class WalletRequest {

    private Long userId;
    private BigDecimal amount;
    private String mode;   // "ADD" or "DEDUCT"
    private String reference_id;
    private Map<String, Object> payload;
    private String currency;
    private String provider_txn_id;
    private String provider;
    private String txn_id;
    private String type;

    public WalletRequest() {}

    public WalletRequest(Long userId, BigDecimal amount, String mode) {
        this.userId = userId;
        this.amount = amount;
        this.mode = mode;
    }

}
