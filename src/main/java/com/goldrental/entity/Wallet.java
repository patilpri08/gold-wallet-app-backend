package main.java.com.goldrental.entity;

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

    private Long userId;

    // <-- Needed
    private BigDecimal balance;  // <-- Important

    public Wallet() {}

    public Wallet(Long userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // --- Getters and Setters ---

}
