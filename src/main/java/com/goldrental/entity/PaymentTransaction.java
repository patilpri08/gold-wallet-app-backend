package com.goldrental.entity;

import com.goldrental.converter.JpaJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;


@Entity
@Getter
@Setter
@Table(name = "payment_transactions")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relationship to WalletTransaction
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_transaction_id", nullable = false)
    private WalletTransaction walletTransaction;


    @Column(name = "provider_txn_id", nullable = false, unique = true)
    private String provider_txn_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User paymentTransactionUser;

    @Column(name = "txn_date", nullable = false)
    private OffsetDateTime txnDate;

    @Column(name = "type", nullable = false)
    private String type; // e.g., Add Money, Withdrawal, Security Blocked, Refund

    @Column(name = "jewellery_name")
    private String jewelleryName;

    @Column(name = "duration")
    private String duration;

    @Column(name = "amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "mode")
    private String mode; // Wallet, UPI, Bank, etc.

    @Convert(converter = JpaJsonConverter.class)
    @Column(name = "meta", columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "status")
    private String status;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "provider")
    private String provider;
}