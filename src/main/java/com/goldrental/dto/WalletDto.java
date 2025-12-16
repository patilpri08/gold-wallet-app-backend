package com.goldrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private Long userId;
    private BigDecimal balance;
    private LocalDateTime updated_at;

}