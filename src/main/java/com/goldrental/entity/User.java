package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(unique = true)
    private String phone;

    @OneToOne(mappedBy = "walletUser", cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToOne(mappedBy = "jewellerUser", cascade = CascadeType.ALL)
    private Jeweller jeweller;

    @Column(nullable = false)
    private String role; // e.g. "ROLE_CUSTOMER", "ROLE_ADMIN", "ROLE_JEWELLER"

}