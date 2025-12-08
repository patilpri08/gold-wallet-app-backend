package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterJweller {

    // Login credentials
    private String email;
    private String password;

    // Business profile
    private String businessName;
    private String ownerName;
    private String address;
    private String phone;

    // GST / Store Docs
    private String gstNumber;
    private String storeDocs;

    // Bank details
    private String accountNumber;
    private String ifscCode;

    // Staff login (optional)
    private String staffEmail;

    // KYC
    private String kycType;
    private String kycNumber;

    // Store info
    private String storeTimings;
    private Boolean verified;
}
