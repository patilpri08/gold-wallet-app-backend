package main.java.com.goldrental.dto;

import java.math.BigDecimal;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
