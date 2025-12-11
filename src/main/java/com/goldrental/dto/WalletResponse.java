package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class WalletResponse {

    int balance;
    int blocked;
    int available;

    public WalletResponse() {}
    public WalletResponse(int balance, int blocked, int available) {
        this.balance = balance;
        this.blocked = blocked;
        this.available = available;

    }
}
