package com.goldrental.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jewellers")
public class Jeweller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    // <-- Getter
    private boolean verified; // <-- Add this field

    public Jeweller() {}

    public Jeweller(String name, String email, boolean verified) {
        this.name = name;
        this.email = email;
        this.verified = verified;
    }

}
