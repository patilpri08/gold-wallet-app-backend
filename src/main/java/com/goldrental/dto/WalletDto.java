package com.goldrental.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class WalletDto {
    private Long userId;
    private BigDecimal balance;
    private List<TransactionDto> transactions;

    @Data
    public static class TransactionDto {
        private Long id;
        private BigDecimal amount;
        private String type;        // CREDIT | DEBIT
        private String description;
        private String date;
    }
}
