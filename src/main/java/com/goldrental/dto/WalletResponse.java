package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletResponse {

    BigDecimal balance;
    BigDecimal blocked;
    BigDecimal available;

    public WalletResponse() {}
    public WalletResponse(BigDecimal balance, BigDecimal blocked, BigDecimal available) {
        this.balance = balance;
        this.blocked = blocked;
        this.available = available;
    }
}
