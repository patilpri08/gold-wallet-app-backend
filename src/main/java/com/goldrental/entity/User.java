package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // add this getter

    @Column(unique = true)
    private String email;

    private String password;
    private String role; // CUSTOMER or JEWELLER

    // relations
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @OneToMany(mappedBy = "customer")
    private Set<Rental> rentals;

    @Column(unique = true)
    private String phone;


}
