package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRequest {

    private Long userId;
    private BigDecimal amount;
    private String type;   // "ADD" or "DEDUCT"

    public WalletRequest() {}

    public WalletRequest(Long userId, BigDecimal amount, String type) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
    }

}
