package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
@Table(name = "payment_transactions")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txn_id", nullable = false, unique = true)
    private String txnId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User jewelleryItemUser;

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

//    @Convert(converter = JpaJsonConverter.class)
//    @Column(name = "meta", columnDefinition = "jsonb")
//    private Map<String, Object> meta;

    // getters and setters
    // equals, hashCode, toString
}