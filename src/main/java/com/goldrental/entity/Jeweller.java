package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "jewellers")
public class Jeweller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User jewellerUser;   // contains email + password

    @Column(nullable = false, length = 150)
    private String businessName;

    @Column(nullable = false, length = 100)
    private String ownerName;

    @Column(nullable = false)
    private String address;

    @Column(length = 15)
    private String phone;

    @Column(nullable = false, length = 20)
    private String gstNumber;

    private String storeDocs;

    @Column(nullable = false, length = 30)
    private String accountNumber;

    @Column(nullable = false, length = 20)
    private String ifscCode;

    private String staffEmail;

    @Column(nullable = false, length = 50)
    private String kycType;

    @Column(nullable = false, length = 50)
    private String kycNumber;

    @Column(nullable = false)
    private String storeTimings;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    private boolean verified = false;

    public Jeweller() {}

    public String getPassword() {
        return this.jewellerUser.getPassword();
    }

    public String getEmail() {
        return this.jewellerUser.getEmail();
    }
}