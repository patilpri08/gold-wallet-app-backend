package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(nullable = false)
    private String type; // CREDIT or DEBIT

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(unique = true)
    private String reference_id;

    private String description;

    @Column(nullable = false)
    private String status; // SUCCESS, FAILED, PENDING

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "balance_after", nullable = false)
    private BigDecimal balanceAfter;

}