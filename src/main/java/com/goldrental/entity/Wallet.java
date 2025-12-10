package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // <-- Needed
    private BigDecimal balance;  // <-- Important

    public Wallet() {
    }

    @OneToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private User walletUser;

}
